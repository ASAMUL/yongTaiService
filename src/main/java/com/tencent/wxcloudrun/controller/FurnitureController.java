package com.tencent.wxcloudrun.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.tencent.wxcloudrun.entity.Furniture;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 家具表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/furniture")
public class FurnitureController {


    @Autowired
    private FurnitureService furnitureService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Furniture>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Furniture> aPage = furnitureService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Furniture> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(furnitureService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Furniture params) {
        furnitureService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        furnitureService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Furniture params) {
        furnitureService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
