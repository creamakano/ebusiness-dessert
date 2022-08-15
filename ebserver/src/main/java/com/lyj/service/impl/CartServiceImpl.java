package com.lyj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.Cart;
import com.lyj.entity.User;
import com.lyj.mapper.CartMapper;
import com.lyj.service.CartService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired(required = false)
    private CartMapper cartMapper;

    @Override
    public JSONResult<Void> addToCart(HttpSession session, Integer goodsId , Integer nums) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<>(202,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>()
                .eq("user_id",uid).eq("goods_id",goodsId));
        int row = 0;
        if(cart!=null){//购物车已有记录，做数量增加
            int orNums = cart.getNums();
            cart.setNums(orNums+nums);
            row = cartMapper.updateById(cart);
        }else {//购物车无记录，做插入操作
            Cart cart1 = new Cart().setGoodsId(goodsId).setUserId(uid).setNums(nums);
            row = cartMapper.insert(cart1);
        }

        if(row!=1){
            return new JSONResult<>(203,"加入购物车失败，稍后重试");
        }
        return new JSONResult<>(200,"加入购物车成功");

    }

    @Override
    public String myCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        Integer uid = user.getUserId();
        List<Cart> carts = cartMapper.selectCartInfoByUid(uid);
        int nums = 0;
        double total = 0;
        for(Cart cart : carts){
            nums+=cart.getNums();
            total= total+cart.getNums()*cart.getGoods().getGoodsRuPrice();
        }

        model.addAttribute("carts",carts);
        model.addAttribute("nums",nums);
        model.addAttribute("total",total);
        return "reception/cart";
    }

    @Override
    public String deleteCartById(Integer cartId) {
        int row = cartMapper.deleteById(cartId);
        if (row!=1){
            System.out.println("操作删除失败");
        }
        return "redirect:myCart";
    }

    @Override
    public String clearCart(HttpSession session) {
        User user =(User)session.getAttribute("user");
        Integer userId = user.getUserId();
        cartMapper.delete(new QueryWrapper<Cart>().eq("user_id",userId));
        return "redirect:myCart";
    }

}
