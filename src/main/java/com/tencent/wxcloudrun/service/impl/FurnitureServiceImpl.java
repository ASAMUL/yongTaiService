package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.tencent.wxcloudrun.dao.FurnitureaccessoryMapper;
import com.tencent.wxcloudrun.entity.Furniture;
import com.tencent.wxcloudrun.dao.FurnitureMapper;
import com.tencent.wxcloudrun.entity.FurnitureType;
import com.tencent.wxcloudrun.entity.Furnitureaccessory;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.service.FurnitureaccessoryService;
import com.tencent.wxcloudrun.service.FurnituretypeService;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import com.tencent.wxcloudrun.vo.FurnitureVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    @Override
    public Result<List<FurnitureVO>> queryFurnitureByType(String type,String isParent) {
        log.info("根据类型查询家具开始====》,type:{},isParent:{}",type,isParent);
        List<Furniture> list = new ArrayList<>();
        if (StrUtil.isNotBlank(isParent)){
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
                        .list();
            }

        }else {
            list = this.lambdaQuery()
                    .eq(Furniture::getFTypeId, type)
                    .list();
        }
        if (CollUtil.isEmpty(list)){
            return Result.OK(Collections.emptyList());
        }
        // 查询配件名称
        List<String> typeIds = new ArrayList<>();
        list.forEach(item -> {
            if (StrUtil.isNotBlank(item.getFAId())) {
                String[] split = item.getFAId().split(",");
                typeIds.addAll(Arrays.asList(split));
            }
        });
        // 查询公共配件

        List<FurnitureAccessoryVO> accessories = furnitureaccessoryMapper.queryByFurnitureIds(typeIds);
        List<FurnitureVO> collect = list.stream()
                .map(item -> BeanUtil.copyProperties(item, FurnitureVO.class))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(accessories)){
            collect.forEach(item -> {
                List<FurnitureAccessoryVO> r = accessories.stream()
                        .filter(accessory -> {
                            String[] split = item.getFAId().split(",");
                            return Arrays.asList(split).contains(Convert.toStr(accessory.getFAId())) || "1".equals(accessory.getIsPublic());
                        })
                        .collect(Collectors.toList());
                item.setFurnitureAccessoryList(r);
                if (CollUtil.isNotEmpty(r)){
                    if (StrUtil.isNotBlank(item.getFAId())){
                        String[] split = item.getFAId().split(",");
                        List<String> faNames = r.stream()
                                .filter(fa -> Arrays.asList(split)
                                        .contains(Convert.toStr(fa.getFAId())))
                                .map(FurnitureAccessoryVO::getFAName).collect(Collectors.toList());
                        item.setFAName(StrUtil.join(",",faNames));

                    }
                }
            });
        }
        return Result.OK(collect);
    }

    @Override
    public Result<List<FurnitureVO>> getBySearch(String name) {
        log.info("根据名称查询家具开始====》,name:{}",name);
        List<Furniture> list = this.lambdaQuery()
                .eq(Furniture::getIsDeleted, 0)
                .like(Furniture::getFName, name)
                .list();
        if (CollUtil.isEmpty(list)){
            return Result.OK(Collections.emptyList());
        }
        // 查询配件名称
        List<String> typeIds = new ArrayList<>();
        list.forEach(item -> {
            if (StrUtil.isNotBlank(item.getFAId())) {
                String[] split = item.getFAId().split(",");
                typeIds.addAll(Arrays.asList(split));
            }
        });
        // 查询公共配件
        List<FurnitureAccessoryVO> accessories = furnitureaccessoryMapper.queryByFurnitureIds(typeIds);
        List<FurnitureVO> collect = list.stream()
                .map(item -> BeanUtil.copyProperties(item, FurnitureVO.class))
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(accessories)){
            collect.forEach(item -> {
                List<FurnitureAccessoryVO> r = accessories.stream()
                        .filter(accessory -> {
                            String[] split = item.getFAId().split(",");
                            return Arrays.asList(split).contains(Convert.toStr(accessory.getFAId())) || "1".equals(accessory.getIsPublic());
                        })
                        .collect(Collectors.toList());
                item.setFurnitureAccessoryList(r);
               if (CollUtil.isNotEmpty(r)){
                  if (StrUtil.isNotBlank(item.getFAId())){
                      String[] split = item.getFAId().split(",");
                      List<String> faNames = r.stream()
                              .filter(fa -> Arrays.asList(split)
                                      .contains(Convert.toStr(fa.getFAId())))
                              .map(FurnitureAccessoryVO::getFAName).collect(Collectors.toList());
                        item.setFAName(StrUtil.join(",",faNames));

                  }
               }
            });
        }
        return Result.OK(collect);
    }
}
