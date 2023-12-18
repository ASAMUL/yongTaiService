package com.tencent.wxcloudrun.controller;

import cn.hutool.core.bean.BeanUtil;
import com.tencent.wxcloudrun.entity.Furnitureaccessory;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.service.FurnitureaccessoryService;
import com.tencent.wxcloudrun.vo.FurnitureAccessoryVO;
import com.tencent.wxcloudrun.vo.FurnitureVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.tencent.wxcloudrun.entity.Furniture;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 家具表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/api/furniture")
@RequiredArgsConstructor
public class FurnitureController {

    private final FurnitureService furnitureService;
    private final FurnitureaccessoryService furnitureaccessoryService;

    @GetMapping(value = "/queryFurnitureByType")
    public Result<List<FurnitureVO>> list(@RequestParam("type") String type,@RequestParam(value = "isParent",required = false) String isParent) {
        return furnitureService.queryFurnitureByType(type,isParent);

    }
    @GetMapping(value = "/getBySearch")
    public Result<List<FurnitureVO>> getBySearch(@RequestParam("name") String name) {
        return furnitureService.getBySearch(name);

    }
    @GetMapping(value = "/{id}")
    public Result<FurnitureVO> getById(@PathVariable("id") String id) {
        Furniture byId = furnitureService.getById(id);
        FurnitureVO vo = BeanUtil.copyProperties(byId, FurnitureVO.class);
        Furnitureaccessory fa = furnitureaccessoryService.getById(byId.getFAId());
        vo.setFAName(fa.getFAName());
        // 获取配件
        List<FurnitureAccessoryVO> accessories = furnitureaccessoryService.queryByFurnitureId(byId.getFAId());
        vo.setFurnitureAccessoryList(accessories);
        return Result.OK(vo);
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
