package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.CartForm;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-16
 */
public interface CartService extends IService<Cart> {
    /**
     * 添加购物车
     * @param form 购物车表单
     * @return 添加结果
     */
    Result<String> addCart(CartForm form);

    /**
     * 获取购物车
     * @return 购物车列表
     */
    Result<List<Cart>> getCart();
}
