package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.tencent.wxcloudrun.entity.Furniturepromotion;
import com.tencent.wxcloudrun.dao.FurniturepromotionMapper;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.service.FurniturepromotionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.vo.FurniturePromotionVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 家具折扣方案表 服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
public class FurniturepromotionServiceImpl extends ServiceImpl<FurniturepromotionMapper, Furniturepromotion> implements FurniturepromotionService {

    @Override
    public Result<List<FurniturePromotionVO>> listByDate() {
        List<Furniturepromotion> list = this.lambdaQuery()
                .eq(Furniturepromotion::getIsDeleted, 0)
                .lt(Furniturepromotion::getFPStartTime, new Date())
                .gt(Furniturepromotion::getFPEndTime, new Date())
                .list();
        List<FurniturePromotionVO> collect = list.stream()
                .map(item -> BeanUtil.copyProperties(item, FurniturePromotionVO.class))
                .collect(Collectors.toList());
        return Result.OK(collect);
    }
}
