package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.constants.RedisKeys;
import com.tencent.wxcloudrun.dao.FurnitureMapper;
import com.tencent.wxcloudrun.dao.FurnitureaccessoryMapper;
import com.tencent.wxcloudrun.entity.Furniture;
import com.tencent.wxcloudrun.entity.FurnitureType;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.tencent.wxcloudrun.service.FurnitureaccessoryService;
import com.tencent.wxcloudrun.service.FurnituretypeService;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import com.tencent.wxcloudrun.vo.FurnitureVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 家具表 服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FurnitureServiceImpl extends ServiceImpl<FurnitureMapper, Furniture> implements FurnitureService {
    private final FurnituretypeService furnituretypeService;
    private final FurnitureaccessoryService furnitureaccessoryService;
    private final FurnitureaccessoryMapper furnitureaccessoryMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Result<List<FurnitureVO>> queryFurnitureByType(String type, String isParent) {
        log.info("根据类型查询家具开始====》,type:{},isParent:{}", type, isParent);
        String furnitureByRedis = redisTemplate.opsForValue().get(RedisKeys.FURNITURE_BY_TYPE);
        if (StrUtil.isNotBlank(furnitureByRedis)) {
            return Result.OK(JSONUtil.toList(furnitureByRedis, FurnitureVO.class));
        }
        List<Furniture> list = new ArrayList<>();
        if (StrUtil.isNotBlank(isParent)) {
            // 查询父级类型
            List<Integer> typeIds = furnituretypeService.lambdaQuery()
                    .eq(FurnitureType::getFTParentId, type)
                    .list()
                    .stream()
                    .map(FurnitureType::getFTId)
                    .collect(Collectors.toList());
            if (CollUtil.isNotEmpty(typeIds)) {
                typeIds.add(Convert.toInt(type));
                list = this.lambdaQuery()
                        .in(Furniture::getFTypeId, typeIds)
                        .eq(Furniture::getIsDeleted, "0")
                        .list();
            }

        } else {
            list = this.lambdaQuery()
                    .eq(Furniture::getFTypeId, type)
                    .eq(Furniture::getIsDeleted, "0")
                    .list();
        }
        if (CollUtil.isEmpty(list)) {
            return Result.OK(Collections.emptyList());
        }
        // 查询配件名称
        List<String> typeIds = new ArrayList<>();
        List<Integer> ffIds = new ArrayList<>();
        list.forEach(item -> {
            if (StrUtil.isNotBlank(item.getFAId())) {
                String[] split = item.getFAId().split(",");
                typeIds.addAll(Arrays.asList(split));
            }
            if (ObjectUtil.isNotNull(item.getFFactoryId())) {
                ffIds.add(item.getFFactoryId());
            }
        });
        // 查询公共配件

        List<FurnitureAccessoryVO> accessories = furnitureaccessoryMapper.queryByFurnitureIds(typeIds,ffIds);
        List<FurnitureVO> collect = list.stream()
                .map(item -> BeanUtil.copyProperties(item, FurnitureVO.class))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(accessories)) {
            collect.forEach(item -> {
                if ("1".equals(item.getHasAccessory())) {
                    List<FurnitureAccessoryVO> r = accessories.stream()
                            .filter(accessory -> {
                                if (ObjectUtil.notEqual(accessory.getFATId(), item.getFTypeId())) {
                                    return false;
                                }
                                if ("1".equals(accessory.getIsPublic())) {
                                    if (ObjectUtil.isNull(accessory.getFAFId())) {
                                        return true;
                                    }
                                    return ObjectUtil.isNotNull(accessory.getFAFId()) && ObjectUtil.equal(accessory.getFAFId(), item.getFFactoryId());
                                }
                                if (ObjectUtil.isNotNull(accessory.getFAFId())) {
                                    return ObjectUtil.equal(accessory.getFAFId(), item.getFFactoryId());
                                }
                                if (StrUtil.isNotBlank(item.getFAId())) {
                                    List<String> ids = Arrays.asList(item.getFAId().split(","));
                                    return ids.contains(Convert.toStr(accessory.getFAId()));
                                }
                                return false;
                            })
                            .collect(Collectors.toList());

                    item.setFurnitureAccessoryList(r);
                    if (CollUtil.isNotEmpty(r)) {
                        if (StrUtil.isNotBlank(item.getFAId())) {
                            String[] split = item.getFAId().split(",");
                            List<String> faNames = r.stream()
                                    .filter(fa -> Arrays.asList(split)
                                            .contains(Convert.toStr(fa.getFAId())) && equalsPriceZreo(fa.getFAPrice()))
                                    .map(FurnitureAccessoryVO::getFAName).collect(Collectors.toList());
                            item.setFAName(StrUtil.join(",", faNames));

                        }
                    }
                }


            });
        }
        redisTemplate.opsForValue().set(RedisKeys.FURNITURE_BY_TYPE,JSONUtil.toJsonStr(collect),1, TimeUnit.HOURS);
        return Result.OK(collect);
    }
    private boolean equalsPriceZreo(BigDecimal price){
        return ObjectUtil.isNull(price) || price.equals(new BigDecimal("0.00")) || price.equals(new BigDecimal("0.0")) || price.equals(new BigDecimal("0"));
    }
    @Override
    public Result<List<FurnitureVO>> getBySearch(String name) {
        log.info("根据名称查询家具开始====》,name:{}", name);
        List<Furniture> list = this.lambdaQuery()
                .eq(Furniture::getIsDeleted, 0)
                .like(Furniture::getFName, name)
                .list();
        if (CollUtil.isEmpty(list)) {
            return Result.OK(Collections.emptyList());
        }
        // 查询配件名称
        List<String> typeIds = new ArrayList<>();
        List<Integer> ffIds = new ArrayList<>();
        list.forEach(item -> {
            if (StrUtil.isNotBlank(item.getFAId())) {
                String[] split = item.getFAId().split(",");
                typeIds.addAll(Arrays.asList(split));
            }
            if (ObjectUtil.isNotNull(item.getFFactoryId())) {
                ffIds.add(item.getFFactoryId());
            }
        });
        // 查询公共配件
        List<FurnitureAccessoryVO> accessories = furnitureaccessoryMapper.queryByFurnitureIds(typeIds,ffIds);
        List<FurnitureVO> collect = list.stream()
                .map(item -> BeanUtil.copyProperties(item, FurnitureVO.class))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(accessories)) {
            collect.forEach(item -> {
                if ("1".equals(item.getHasAccessory())) {
                    List<FurnitureAccessoryVO> r = accessories.stream()
                            .filter(accessory -> {
                                if (ObjectUtil.notEqual(accessory.getFATId(), item.getFTypeId())) {
                                    return false;
                                }
                                if ("1".equals(accessory.getIsPublic())) {
                                    if (ObjectUtil.isNull(accessory.getFAFId())) {
                                        return true;
                                    }
                                    return ObjectUtil.isNotNull(accessory.getFAFId()) && ObjectUtil.equal(accessory.getFAFId(), item.getFFactoryId());
                                }
                                if (ObjectUtil.isNotNull(accessory.getFAFId())) {
                                    return ObjectUtil.equal(accessory.getFAFId(), item.getFFactoryId());
                                }
                                if (StrUtil.isNotBlank(item.getFAId())) {
                                    List<String> ids = Arrays.asList(item.getFAId().split(","));
                                    return ids.contains(Convert.toStr(accessory.getFAId()));
                                }
                                return false;
                            })
                            .collect(Collectors.toList());

                    item.setFurnitureAccessoryList(r);
                    if (CollUtil.isNotEmpty(r)) {
                        if (StrUtil.isNotBlank(item.getFAId())) {
                            String[] split = item.getFAId().split(",");
                            List<String> faNames = r.stream()
                                    .filter(fa -> Arrays.asList(split)
                                            .contains(Convert.toStr(fa.getFAId())) && equalsPriceZreo(fa.getFAPrice()))
                                    .map(FurnitureAccessoryVO::getFAName).collect(Collectors.toList());
                            item.setFAName(StrUtil.join(",", faNames));

                        }
                    }
                }

            });
        }
        return Result.OK(collect);
    }

    @Override
    public FurnitureVO getByIdSql(String id) {
        return baseMapper.getById(id);
    }
}
