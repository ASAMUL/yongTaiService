package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.vo.FurniturePromotionVO;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurniturepromotionService;
import com.tencent.wxcloudrun.entity.Furniturepromotion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 家具折扣方案表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/api/furniturePromotion")
public class FurniturepromotionController {


    @Autowired
    private FurniturepromotionService furniturepromotionService;

    @GetMapping
    public Result<List<FurniturePromotionVO>> list() {
        return  furniturepromotionService.listByDate();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Furniturepromotion> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(furniturepromotionService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Furniturepromotion params) {
        furniturepromotionService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        furniturepromotionService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Furniturepromotion params) {
        furniturepromotionService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
