package com.tencent.wxcloudrun.vo;

import lombok.Data;

import java.util.List;

@Data
public class Option {
    private String label;
    private String value;
    private List<Option> children;

}
