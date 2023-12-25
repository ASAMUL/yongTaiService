package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.FurnitureaccessoryMapper;
import com.tencent.wxcloudrun.entity.Furnitureaccessory;
import com.tencent.wxcloudrun.service.FurnitureaccessoryService;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 家具配件表 服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
@Slf4j
public class FurnitureaccessoryServiceImpl extends ServiceImpl<FurnitureaccessoryMapper, Furnitureaccessory> implements FurnitureaccessoryService {

    @Override
    public List<FurnitureAccessoryVO> queryByFurnitureId(String faId, Integer ffId) {
        log.info("查询家具配件, faId:{}", faId);
        List<String> ids = new ArrayList<>();
        if (StrUtil.isNotBlank(faId)) {
            if (faId.contains(",")) {
                String[] faIds = faId.split(",");
                // 查询出所有的公共配件和该家具的私有配件
                ids.addAll(Arrays.asList(faIds));
            } else {
                ids.add(faId);
            }
        }
        List<FurnitureAccessoryVO> furnitureAccessoryVOS = baseMapper.queryByFurnitureIds(ids, CollUtil.newArrayList(ffId));
        furnitureAccessoryVOS = furnitureAccessoryVOS.stream()
                .filter(item -> {
                    if ("1".equals(item.getIsPublic())) {
                        if (ObjectUtil.isNull(item.getFAFId())) {
                            return true;
                        }
                        return ObjectUtil.isNotNull(item.getFAFId()) && item.getFAFId().equals(ffId);
                    }
                    if (ObjectUtil.isNotNull(item.getFAFId())) {
                        return ObjectUtil.equal(item.getFAFId(), ffId);
                    }
                    if (CollUtil.isNotEmpty(ids)) {
                        return ids.contains(Convert.toStr(item.getFAId()));
                    }
                    return false;
                })
                .collect(Collectors.toList());
                        // 查询出所有的公共配件
//        List<Furnitureaccessory> list = this.lambdaQuery()
//                .eq(Furnitureaccessory::getIsPublic, 1)
//                .eq(Furnitureaccessory::getIsDeleted, 0)
//                .notIn(CollUtil.isNotEmpty(ids), Furnitureaccessory::getFAId, ids)
//                .list();
//        if (CollUtil.isNotEmpty(ids)) {
//            List<Furnitureaccessory> privateFa = this.lambdaQuery()
//                    .in(Furnitureaccessory::getFAId, ids)
//                    .list();
//            list.addAll(privateFa);
//        }

        return furnitureAccessoryVOS;
    }

    private boolean equalsPriceZreo(BigDecimal price) {
        return price.equals(new BigDecimal("0.00")) || price.equals(new BigDecimal("0.0")) || price.equals(new BigDecimal("0"));
    }
}
