package com.lyj.controller.backstage;

import com.lyj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
public class BackOrderController {
    public static final Integer ORDERPAGESIZE = 6;//页面大小

    @Autowired
    private OrderService orderService;

    @GetMapping("selectOrder")
    public String selectOrder(Integer currentPage ,  Model model){
        return orderService.selectOrderByPage(currentPage,ORDERPAGESIZE,model);
    }
}
