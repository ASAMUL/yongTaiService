package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tencent.wxcloudrun.entity.Furnitureaccessory;
import com.tencent.wxcloudrun.dao.FurnitureaccessoryMapper;
import com.tencent.wxcloudrun.service.FurnitureaccessoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<FurnitureAccessoryVO> queryByFurnitureId(String faId) {
        log.info("查询家具配件, faId:{}", faId);
        Set<String> ids = new HashSet<>();
        if (StrUtil.isNotBlank(faId)) {
            if (faId.contains(",")) {
                String[] faIds = faId.split(",");
                // 查询出所有的公共配件和该家具的私有配件
                ids.addAll(Arrays.asList(faIds));
            }else {
                ids.add(faId);
            }
        }

        // 查询出所有的公共配件
        List<Furnitureaccessory> list = this.lambdaQuery()
                .eq(Furnitureaccessory::getIsPublic, 1)
                .eq(Furnitureaccessory::getIsDeleted, 0)
                .notIn(CollUtil.isNotEmpty(ids), Furnitureaccessory::getFAId, ids)
                .list();
        if (CollUtil.isNotEmpty(ids)) {
            List<Furnitureaccessory> privateFa = this.lambdaQuery()
                    .in(Furnitureaccessory::getFAId, ids)
                    .list();
            list.addAll(privateFa);
        }

        return list.stream().map(item -> BeanUtil.copyProperties(item, FurnitureAccessoryVO.class)).collect(Collectors.toList());
    }
}
