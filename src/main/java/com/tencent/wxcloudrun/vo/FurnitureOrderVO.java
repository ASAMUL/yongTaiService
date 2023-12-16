package com.tencent.wxcloudrun.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tencent.wxcloudrun.json.FuJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FurnitureOrderVO {

    private String FONo;
    private List<FuJson> furnitureList;
}
