package com.lyj.controller.reception;

import com.lyj.entity.Address;
import com.lyj.service.AddressService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    //新增地址
    @PostMapping("insertAddress")
    public String insertAddress(String address,String consigneeName,String phoneNum , HttpSession session, Model model){
        return addressService.insertAddress(address,consigneeName,phoneNum,session,model);
    }
    //新增地址
    @PostMapping("updateAddress")
    public String updateAddress(String address,String consigneeName,String phoneNum ,Integer id , HttpSession session, Model model){
        return addressService.updateAddress(address,consigneeName,phoneNum,id,session,model);
    }

    @RequestMapping("deleteAddress")
    public String deleteAddress(HttpSession session, Model model,Integer addressId){
        return addressService.deleteAddress(session,model,addressId);
    }
    @RequestMapping("setDefaultAddress")
    @ResponseBody
    public JSONResult<Void> setDefaultAddress(Integer addressId, HttpSession session){
        return addressService.setDefaultAddress(addressId,session);
    }
    @GetMapping("getAddress")
    @ResponseBody
    public JSONResult<List<Address>> getAddress(HttpSession session){
        return addressService.getAddress(session);
    }


}
