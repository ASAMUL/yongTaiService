package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.Option;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurnituretypeService;
import com.tencent.wxcloudrun.entity.FurnitureType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 家具类型表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/api/furnitureType")
public class FurnituretypeController {


    @Autowired
    private FurnituretypeService furnituretypeService;

    @GetMapping
    public Result<List<Option>> furnitureType() {
        return furnituretypeService.furnitureType();
    }

}
