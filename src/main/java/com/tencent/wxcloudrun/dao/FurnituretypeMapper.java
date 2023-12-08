package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.FurnitureType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.vo.Option;

import java.util.List;

/**
 * <p>
 * 家具类型表 Mapper 接口
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnituretypeMapper extends BaseMapper<FurnitureType> {
    /**
     * 家具类型
     * @return 家具类型
     */
    List<Option> furnitureType();
}
