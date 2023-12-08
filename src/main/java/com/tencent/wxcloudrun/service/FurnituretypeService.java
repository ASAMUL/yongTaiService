package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.FurnitureType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.Option;

import java.util.List;

/**
 * <p>
 * 家具类型表 服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnituretypeService extends IService<FurnitureType> {
    /**
     * 家具类型
     * @return 家具类型
     */
    Result<List<Option>> furnitureType();
}
