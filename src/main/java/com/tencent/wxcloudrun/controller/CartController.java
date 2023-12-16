package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.CartForm;
import com.tencent.wxcloudrun.service.CartService;
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
 * @since 2023-12-16
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public Result<String> addCart(@RequestBody CartForm form) {
        return cartService.addCart(form);
    }
    @GetMapping
    public Result<List<CartVO>> getCart() {
        return cartService.getCart();
    }
    @PostMapping("/delete")
    public Result<String> deleteCart(@RequestBody List<Integer> ids) {
        boolean b = cartService.removeBatchByIds(ids);
        return Result.OK("删除成功");
    }



}
