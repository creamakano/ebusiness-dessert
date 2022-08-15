package com.lyj.service;

import com.lyj.entity.Order;
import org.springframework.ui.Model;

public interface PayService {
    /**
     * 跳转到支付页面
     * @param order 订单数据
     * @param model
     * @return
     */
    String toPay(Order order, Model model);
}
