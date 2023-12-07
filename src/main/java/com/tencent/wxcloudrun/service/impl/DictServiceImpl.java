package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.tencent.wxcloudrun.entity.Dict;
import com.tencent.wxcloudrun.dao.DictMapper;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.vo.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public Result<List<Option>> getByCode(String code, HttpServletRequest request) {
        log.info("查询字典表 code:{}", code);
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        headers.forEach((k, v) -> log.info("header:{} value:{}", k, v));
        List<Dict> list = this.lambdaQuery()
                .eq(Dict::getDictCode, code)
                .list();
        if (CollUtil.isEmpty(list)) {
            return Result.OK(Collections.emptyList());
        }
        List<Option> collect = list.stream()
                .map(
                        dict -> {
                            Option option = new Option();
                            option.setLabel(dict.getName());
                            option.setValue(dict.getValue());
                            return option;
                        }
                ).collect(Collectors.toList());
        return Result.OK(collect);
    }
}
