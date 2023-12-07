package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.DictService;
import com.tencent.wxcloudrun.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {


    private final DictService dictService;

    @GetMapping("/getByCode")
    public Result<List<Option>> getByCode(@RequestParam String code) {
        return dictService.getByCode(code);
    }
}
