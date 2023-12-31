package com.tencent.wxcloudrun.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurniturefactoryService;
import com.tencent.wxcloudrun.entity.Furniturefactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 家具厂家表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/furniturefactory")
public class FurniturefactoryController {


    @Autowired
    private FurniturefactoryService furniturefactoryService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Furniturefactory>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Furniturefactory> aPage = furniturefactoryService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Furniturefactory> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(furniturefactoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Furniturefactory params) {
        furniturefactoryService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        furniturefactoryService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Furniturefactory params) {
        furniturefactoryService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
