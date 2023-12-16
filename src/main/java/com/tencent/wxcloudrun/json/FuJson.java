package com.tencent.wxcloudrun.json;

import lombok.Data;

import java.util.List;

@Data
public class FuJson {
    private String fuId;
    private String fuImg;
    private String fuNumber;
    private String fuName;
    private String faName;
    private String faId;
    private List<FaListJson> faList;
}
