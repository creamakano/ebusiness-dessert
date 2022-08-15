package com.lyj.service;

import com.alipay.api.AlipayApiException;
import com.lyj.entity.Order;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

public interface OrderService {
    /**
     * 添加新订单
     * @param order   订单的信息
     * @param session 去Uid
     * @param model   存错误信息
     * @return
     */
    String addOrder(Order order, HttpSession session , Model model , RedirectAttributes attr);



    /**
     * 跳转到我的订单页面
     * @param session
     * @param model
     * @return
     */
    String myOrder(HttpSession session , Model model);

    /**
     * 跳转到订单详情页面
     * @param orderId 订单号
     * @param status
     * @param model
     * @return
     */
    String myOrderDetail(Integer orderId, Integer status, Model model);

    String pay(Integer orderId, String orderName, String addressInfo, double totalPrice) throws AlipayApiException;

    String selectMyOrder(Integer orderId, Integer goodsId, Model model);


    //----------------------------------------------------
    //后台服务

    String selectOrderByPage(Integer page , Integer size , Model model);

}
