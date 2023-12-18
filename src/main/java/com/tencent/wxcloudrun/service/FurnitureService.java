package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Furniture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.FurnitureVO;

import java.util.List;

/**
 * <p>
 * 家具表 服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnitureService extends IService<Furniture> {
    /**
     * 根据类型查询家具
     * @param type 类型
     * @param isParent 是否是父级
     * @return 家具
     */
    Result<List<FurnitureVO>> queryFurnitureByType(String type,String isParent);

    /**
     * 根据名称查询家具
     * @param name 名称
     * @return 家具
     */
    Result<List<FurnitureVO>> getBySearch(String name);
}
