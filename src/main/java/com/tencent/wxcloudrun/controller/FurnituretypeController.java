package com.tencent.wxcloudrun.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurnituretypeService;
import com.tencent.wxcloudrun.entity.Furnituretype;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 家具类型表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/furnituretype")
public class FurnituretypeController {


    @Autowired
    private FurnituretypeService furnituretypeService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Furnituretype>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Furnituretype> aPage = furnituretypeService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Furnituretype> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(furnituretypeService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Furnituretype params) {
        furnituretypeService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        furnituretypeService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Furnituretype params) {
        furnituretypeService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
