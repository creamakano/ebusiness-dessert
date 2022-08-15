package com.lyj.controller.reception;

import com.lyj.entity.Order;
import com.lyj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CountService countService;
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;

    @GetMapping("login")
    public String toLoginPage(){
        return "reception/login";
    }
    @GetMapping("/")
    public String index(Model model, HttpSession session,Integer typeId){
        String view = goodsService.showIndexData(model ,session ,typeId);
        return view;
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "reception/login";
    }
    @GetMapping("register")
    public String toRegister(){
        return "reception/register";
    }
    @GetMapping("/index")
    public String toIndex(Model model, HttpSession session,Integer typeId){
        String view = goodsService.showIndexData(model ,session ,typeId);
        return view;
    }
    @GetMapping("goodsDetail")
    public String goodsDetail(Model model,Integer id){
        return goodsService.getGoodsDetail(model,id);
    }

    @GetMapping("myCart")
    public String myCart(HttpSession session,Model model){
        return cartService.myCart(session,model);
    }

    @GetMapping("userInfo")
    public String userInfo(HttpSession session , Model model) {
        return addressService.toUserInfo(session,model);
    }


    @RequestMapping("pay")
    public String pay(@ModelAttribute("order") Order order, Model model){
        return payService.toPay(order,model);
    }
    @RequestMapping("orderPay")
    public String orderPay(Order order, Model model){
        return payService.toPay(order,model);
    }

    @GetMapping("myOrder")
    public String myOrder(HttpSession session , Model model){
        return orderService.myOrder(session,model);
    }

    @GetMapping("myOrderDetail")
    public String myOrderDetail(Integer orderId ,Integer status , Model model){
        return orderService.myOrderDetail(orderId,status ,model);
    }

    @GetMapping("toComments")
    public String toComments(Integer orderId,Model model){
        return commentService.toComment(orderId,model);
    }

    @GetMapping("selectGoodsResult")
    public String selectGoodsResult(String myKey , Model model){
        return goodsService.selectGoodsResult( myKey ,model );
    }


    @GetMapping("aa")
    public String aa(){
        return "reception/comments";
    }
    @GetMapping("bb")
    public String bb(){
        return "redirect:backstage/aa.html";
    }
    @GetMapping("cc")
    public String cc(){
        return "reception/pay";
    }
    @GetMapping("dd")
    public String dd(){
        return "reception/orderDetail";
    }
    @GetMapping("aboutUs")
    public String aboutUs(){
        return "reception/aboutUs";
    }


}
