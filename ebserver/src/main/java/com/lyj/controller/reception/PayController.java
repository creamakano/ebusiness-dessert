package com.lyj.controller.reception;

import com.alipay.api.AlipayApiException;
import com.lyj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("toPay")
    @ResponseBody
    public String pay(Integer orderId , String orderName , String addressInfo ,double totalPrice ) throws AlipayApiException {
        return orderService.pay(orderId,orderName,addressInfo,totalPrice);
    }
}
