package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.tencent.wxcloudrun.config.UserContextHolder;
import com.tencent.wxcloudrun.entity.Cart;
import com.tencent.wxcloudrun.dao.CartMapper;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.CartForm;
import com.tencent.wxcloudrun.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-16
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Override
    public Result<String> addCart(CartForm form) {
        log.info("添加购物车:{}" , JSONUtil.toJsonStr(form));
        Cart cart = BeanUtil.copyProperties(form, Cart.class);
        cart.setUserId(UserContextHolder.getUserId());
        boolean save = this.save(cart);
        if (!save) {
            throw new RuntimeException("添加失败");
        }
        return Result.OK("添加成功");
    }

    @Override
    public Result<List<Cart>> getCart() {
        log.info("获取购物车,当前用户:{}", UserContextHolder.getUserId());
        List<Cart> list = this.lambdaQuery().eq(Cart::getUserId, UserContextHolder.getUserId()).list();
        return Result.OK(list);
    }
}
