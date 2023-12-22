package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.Furniture;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.vo.FurnitureVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 家具表 Mapper 接口
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnitureMapper extends BaseMapper<Furniture> {
    /**
     * 根据id查询家具
     *
     * @param id id
     * @return 家具
     */
    FurnitureVO getById(@Param("id") String id);
}
