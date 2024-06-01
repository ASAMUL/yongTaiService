package com.tencent.wxcloudrun.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.entity.Furniture;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.entity.Suite;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.tencent.wxcloudrun.service.SuiteService;
import com.tencent.wxcloudrun.vo.CartVO;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.FunctionReference;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author leo
 * @since 2024-04-06
 */
@RestController
@RequestMapping("/suite")
@RequiredArgsConstructor
public class SuiteController {

    private final SuiteService suiteService;
    private final FurnitureService furnitureService;

    @GetMapping("/page")
    public Result<List<Suite>> getSuite(@RequestParam("pageNum") Integer pageNum,  @RequestParam("pageSize") Integer pageSize) {
        Page<Suite> page = suiteService.page(new Page<>(pageNum, pageSize));
        List<Suite> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            return Result.OK(Collections.emptyList());
        }
        List<String> fids = new ArrayList<>();

        records.forEach(ele -> {
            ArrayList<String> strings = CollUtil.newArrayList(ele.getFurnitureIds().split(","));
            fids.addAll(strings);
        });
        // 查询家具
        List<Furniture> furnitures = furnitureService.lambdaQuery()
                .in(Furniture::getFId, fids)
                .list();
        records.forEach(ele -> {
            ArrayList<String> strings = CollUtil.newArrayList(ele.getFurnitureIds().split(","));
            List<Furniture> list = furnitures.stream().filter(f -> strings.contains(f.getFId().toString()))
                    .collect(Collectors.toList());
            ele.setFurnitures(list);
        });
        return Result.OK(records);

    }


}
