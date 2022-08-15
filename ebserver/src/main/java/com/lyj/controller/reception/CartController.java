package com.lyj.controller.reception;

import com.lyj.service.CartService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("addToCart")
    @ResponseBody
    public JSONResult<Void> addToCart(Integer goodsId, Integer nums , HttpSession session){
        return cartService.addToCart(session,goodsId,nums);
    }

    @GetMapping("deleteCartById")
    public String deleteCartById(Integer cartId){
        return cartService.deleteCartById(cartId);
    }

    @GetMapping("clearCart")
    public String clearCart(HttpSession session){
        return cartService.clearCart(session);
    }

}
