package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Furniturepromotion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.FurniturePromotionVO;

import java.util.List;

/**
 * <p>
 * 家具折扣方案表 服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurniturepromotionService extends IService<Furniturepromotion> {
    /**
     * 获取折扣方案
     * @return 折扣方案
     */
    Result<List<FurniturePromotionVO>> listByDate();
}
