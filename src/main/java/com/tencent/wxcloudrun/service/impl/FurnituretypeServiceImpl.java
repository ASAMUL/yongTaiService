package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.entity.FurnitureType;
import com.tencent.wxcloudrun.dao.FurnituretypeMapper;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.service.FurnituretypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.vo.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家具类型表 服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
@Slf4j
public class FurnituretypeServiceImpl extends ServiceImpl<FurnituretypeMapper, FurnitureType> implements FurnituretypeService {

    @Override
    public Result<List<Option>> furnitureType() {
        log.info("查询家具类型开始====》");
        List<Option> options = baseMapper.furnitureType();
        return Result.OK(options);
    }
}
