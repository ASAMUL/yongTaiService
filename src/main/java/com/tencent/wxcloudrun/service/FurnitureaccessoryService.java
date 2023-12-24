package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Furnitureaccessory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 家具配件表 服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnitureaccessoryService extends IService<Furnitureaccessory> {
    /**
     * id查询配件
     * @param faId 家具id
     * @return 配件列表
     */
    List<FurnitureAccessoryVO> queryByFurnitureId(String faId,Integer ffId);


}
