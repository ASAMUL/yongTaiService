package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.entity.Furnitureaccessory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 家具配件表 Mapper 接口
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnitureaccessoryMapper extends BaseMapper<Furnitureaccessory> {

    List<FurnitureAccessoryVO> queryByFurnitureIds(@Param("ids") List<String> ids);
}
