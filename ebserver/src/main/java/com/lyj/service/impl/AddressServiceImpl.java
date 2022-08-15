package com.lyj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.Address;
import com.lyj.entity.User;
import com.lyj.mapper.AddressMapper;
import com.lyj.service.AddressService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired(required = false)
    private AddressMapper addressMapper;

    @Override
    public String toUserInfo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        Integer uid = user.getUserId();
        List<Address> addressList = addressMapper.selectList(new QueryWrapper<Address>().eq("user_id",uid));
        model.addAttribute("adds",addressList);
        model.addAttribute("userOrAddr",1);
        return "reception/userInfo";
    }

    @Override
    public String toAddressInfo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        Integer uid = user.getUserId();
        List<Address> addressList = addressMapper.selectList(new QueryWrapper<Address>().eq("user_id",uid));
        model.addAttribute("adds",addressList);
        model.addAttribute("userOrAddr",2);
        return "reception/userInfo";
    }

    @Override
    public String insertAddress(String addressInfo,String consigneeName,String phoneNum , HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        Integer uid = user.getUserId();
        Address address = new Address().setAddressInfo(addressInfo).setConsigneeName(consigneeName).setPhoneNum(phoneNum).setUserId(uid).setIsDefault(0);
        if(addressMapper.selectList(new QueryWrapper<Address>().eq("user_id",uid).eq("is_default",1)).size()==0){
            address.setIsDefault(1);
        }
        int row = addressMapper.insert(address);
        return toAddressInfo(session,model);
    }

    @Override
    public String updateAddress(String addressInfo,String consigneeName,String phoneNum ,Integer addressId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        Integer uid = user.getUserId();

        Address address = addressMapper.selectById(addressId);
        address.setAddressInfo(addressInfo).setConsigneeName(consigneeName).setPhoneNum(phoneNum);
        addressMapper.updateById(address);
        model.addAttribute("userOrAddr",3);
        return toAddressInfo(session,model);
    }

    @Override
    public String deleteAddress(HttpSession session, Model model,Integer addressId) {
        addressMapper.deleteById(addressId);
        return toAddressInfo(session,model);
    }

    @Override
    public JSONResult<Void> setDefaultAddress(Integer addressId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<>(202,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        //查询本用户是否有默认地址
        Address address = addressMapper.selectOne(new QueryWrapper<Address>()
                .eq("user_id",uid).eq("is_default",1));
        //如果有，先将默认地址取消
        if(address!=null){
            address.setIsDefault(0);
            addressMapper.updateById(address);
        }
        //找到要设置为默认地址的address数据并将默认地址字段改为1，进行更新
        Address defaultAddress = addressMapper.selectById(addressId);
        defaultAddress.setIsDefault(1);
        int row = addressMapper.updateById(defaultAddress);
        if(row!=1){
            return new JSONResult<>(202,"修改失败");
        }
        return new JSONResult<>(200,"修改成功");
    }

    @Override
    public JSONResult<List<Address>> getAddress(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<>(202,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        List<Address> addressList = addressMapper.selectList(
                new QueryWrapper<Address>().eq("user_id",uid));
        if (addressList==null){
            return new JSONResult<>(203,"您还未添加地址，请在我的信息中添加地址");
        }
        return new JSONResult<>(200,"查询成功",addressList);
    }
}
