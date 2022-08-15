package com.lyj.service;

import com.lyj.entity.Address;
import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface AddressService{
    /**
     * 跳转到userInfo页面
     * @param session
     * @return
     */
    String toUserInfo(HttpSession session, Model model);
    String toAddressInfo(HttpSession session, Model model);

    /**
     * 新增收货地址
     * @param addressInfo
     * @param session
     * @return
     */
    String insertAddress(String addressInfo ,String consigneeName,String phoneNum,  HttpSession session, Model model);
    /**
     * 更改收货地址
     * @param addressInfo
     * @param session
     * @return
     */
    String updateAddress(String addressInfo,String consigneeName,String phoneNum  ,Integer addressId, HttpSession session, Model model);

    /**
     * 根据地址id删除地址
     * @param addressId
     * @return
     */
    String deleteAddress(HttpSession session, Model model, Integer addressId);

    /**
     * 设置默认地址
     * @param addressId 要设置默认地址的地址id
     * @return          成功与否的ajax
     */
    JSONResult<Void> setDefaultAddress(Integer addressId, HttpSession session);

    /**
     *
     * @param session 获取用户id
     * @return        返回地址信息的json
     */
    JSONResult<List<Address>> getAddress(HttpSession session);
}
