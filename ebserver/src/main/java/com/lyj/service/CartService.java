package com.lyj.service;

import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface CartService {
    /**
     *  加入购物车
     *
     * @param session 用于获取UserId
     * @param goodsId 要加入购物车的商品Id
     * @param nums    要加入购物车的商品数量
     * @return
     */
    JSONResult<Void> addToCart(HttpSession session, Integer goodsId, Integer nums);

    /**
     * 页面跳转
     *     跳转到我的购物车
     * @param session 用于获取用户id
     * @param model   用于存储购物车数据
     * @return        视图
     */
    String myCart(HttpSession session, Model model);

    /**
     * 购物车页面删除单个商品
     * @param cartId 获取商品id
     * @return
     */
    String deleteCartById(Integer cartId);

    /**
     * 清空购物车
     * @return
     */
    String clearCart(HttpSession session);
}
