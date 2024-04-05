package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.entity.Suite;
import com.tencent.wxcloudrun.service.SuiteService;
import com.tencent.wxcloudrun.vo.CartVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/page")
    public Result<List<Suite>> getSuite(@RequestParam("pageNum") Integer pageNum,  @RequestParam("pageSize") Integer pageSize) {
        Page<Suite> page = suiteService.page(new Page<>(pageNum, pageSize));
        List<Suite> records = page.getRecords();
        return Result.OK(records);

    }


}
