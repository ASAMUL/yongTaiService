package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class BaseRequest {
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer pageNum;
}
