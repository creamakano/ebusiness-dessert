package com.lyj.service.impl;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.*;
import com.lyj.mapper.*;
import com.lyj.service.OrderService;
import com.lyj.utils.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private OrderDetailMapper orderDetailMapper;
    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private GoodsMapper goodsMapper;
    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Override
    public String addOrder(Order order, HttpSession session , Model model, RedirectAttributes attr) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            model.addAttribute("url","/toIndex");
            //return "error";
            //todo
        }
        Integer uid = user.getUserId();

        //创建订单对象
        order.setCreateDate(new Date()).setModifyDate(new Date())
                .setIsDelete(0).setStatus(0).setUserId(uid).setIsComment(0);
        //插入订单表
        int row = orderMapper.insert(order);

        if(row!=1){
            model.addAttribute("errorMsg","提交订单失败，请稍后重试");
            model.addAttribute("url","/count");
            //return "error";
            //todo
        }
        Integer orderId = order.getOrderId();

        //遍历购物车记录，并将订单详情写入订单详情表
        //更新库存
        List<Cart> cartList = cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id",uid));
        for (Cart cart : cartList){
            OrderDetail orderDetail = new OrderDetail()
                    .setOrderId(orderId).setGoodsId(cart.getGoodsId())
                    .setNums(cart.getNums());
            orderDetailMapper.insert(orderDetail);
            goodsMapper.updateStore(cart.getNums(),cart.getGoodsId());
        }

        //订单提交成功，清空购物车
        cartMapper.delete(new QueryWrapper<Cart>().eq("user_id",uid));
        attr.addFlashAttribute("order",order);



        return "redirect:pay";
    }




    @Override
    public String myOrder(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        Integer uid = user.getUserId();
        List<Order> orderList = orderMapper.selectList(new QueryWrapper<Order>().eq("user_id",user.getUserId()));
        model.addAttribute("myOrders",orderList);
        return "reception/myOrder";
    }

    @Override
    public String myOrderDetail(Integer orderId, Integer status, Model model) {
        List<OrderDetail> orderDetailList = orderDetailMapper.selectOrderDetailByOrderId(orderId);
        model.addAttribute("orderDetailList",orderDetailList);
        model.addAttribute("status",status);
        return "reception/orderDetail";

    }

    @Override
    public String pay(Integer orderId, String orderName, String addressInfo, double totalPrice) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.gatewayUrl,
                AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //这里设置支付后跳转的地址
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        String out_trade_no = String.valueOf(new Date().getTime()) + String.valueOf(orderId);
        String total_amount = String.valueOf(totalPrice);
        String subject = orderName;
        String body =null;
        String timeout_express = "5m";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = alipayClient.pageExecute(alipayRequest).getBody();

        paySuccess(orderId);
        return form;
    }

    @Override
    public String selectMyOrder(Integer orderId, Integer goodsId,Model model) {
        OrderDetail orderDetail = commentMapper.selectGoodsByOrderIdAndGoodsId(orderId,goodsId);
        Comment comment = commentMapper.selectOne(new QueryWrapper<Comment>().eq("order_id",orderId).eq("goods_id",goodsId));
        Integer flag = 0;
        String commentContent = "";
        if(comment!=null){
            flag=1;
            commentContent=comment.getContent();
        }
        model.addAttribute("goods",orderDetail);
        model.addAttribute("flag",flag);
        model.addAttribute("commentContent",commentContent);
        return "reception/myComments";
    }

    public void paySuccess(Integer orderId) {
        //更新订单状态
        Order order = orderMapper.selectById(orderId);
        order.setStatus(1);
        order.setModifyDate(new Date());
        orderMapper.updateById(order);

    }




    //-----------------------------------------------------------------------
    //后台服务


    @Override
    public String selectOrderByPage(Integer page, Integer size, Model model) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer index = (page - 1) * size;
        List<Order> orderList = orderMapper.selectOrderByPage(index,size);
        Integer nums = orderMapper.selectList(new QueryWrapper<>()).size();
        Integer totalPage = (int) Math.ceil((double) nums / (double) size);
        model.addAttribute("orderList",orderList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "backstage/allOrder";
    }



}
