package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.Option;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface DictService extends IService<Dict> {
    /**
     * 根据code获取字典
     * @param code 字典code
     * @return 字典
     */

    Result<List<Option>> getByCode(String code);
}
