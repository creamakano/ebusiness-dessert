package com.lyj.controller.reception;

import com.lyj.entity.Order;
import com.lyj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("submitOrder")
    public String submitOrder(Order order, HttpSession session , Model model, RedirectAttributes attr){
        return orderService.addOrder(order,session,model,attr);
    }

    @GetMapping("selectMyOrder")
    public String selectMyOrder(Integer orderId,Integer goodsId,Model model){
        return orderService.selectMyOrder(orderId,goodsId,model);
    }

}
