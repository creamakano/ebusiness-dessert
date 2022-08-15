package com.lyj.service.impl;


import com.lyj.entity.Address;
import com.lyj.entity.Order;
import com.lyj.mapper.AddressMapper;
import com.lyj.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PayServiceImpl implements PayService {

    @Autowired(required = false)
    private AddressMapper addressMapper;

    @Override
    public String toPay(Order order, Model model) {
        model.addAttribute("order",order);
        model.addAttribute("addressInfo",order.getReceiptInfo());
        return "reception/pay";
    }
}
