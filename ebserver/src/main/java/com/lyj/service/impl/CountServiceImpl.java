package com.lyj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.Address;
import com.lyj.entity.Cart;
import com.lyj.entity.Goods;
import com.lyj.entity.User;
import com.lyj.mapper.AddressMapper;
import com.lyj.mapper.CartMapper;
import com.lyj.mapper.GoodsMapper;
import com.lyj.service.CountService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CountServiceImpl implements CountService {

    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private AddressMapper addressMapper;
    @Autowired(required = false)
    private GoodsMapper goodsMapper;

    @Override
    public String toCount(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "error";
        }
        Integer uid = user.getUserId();
        List<Cart> carts = cartMapper.selectCartInfoByUid(uid);
        Address address = addressMapper.selectOne(new QueryWrapper<Address>()
                .eq("user_id",uid).eq("is_default",1));
        Integer flag = 1;
        if(address==null){
            address=new Address().setAddressId(0).setAddressInfo("");
            flag=0;
        }

        int nums = 0;
        double total = 0;
        String orderGoodsName= "";

        for(Cart cart : carts){
            nums+=cart.getNums();
            total= total+cart.getNums()*cart.getGoods().getGoodsRuPrice();
            orderGoodsName= orderGoodsName+ cart.getGoods().getGoodsName()+",";
        }

        model.addAttribute("carts",carts);
        model.addAttribute("nums",nums);
        model.addAttribute("total",total);
        model.addAttribute("orderGoodsName",orderGoodsName);
        model.addAttribute("flag",flag);
        model.addAttribute("defaultAddress",address);
        return "reception/count";
    }

    @Override
    public JSONResult<Void> checkCount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<> (201,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        List<Cart> cart = cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id",uid));
        if(cart==null||cart.size()==0){
            return new JSONResult<>(202,"您的购物车中没有商品进行结算");
        }else {
            return new JSONResult<>(200,"可以结算");
        }
    }

    @Override
    public JSONResult<Void> checkStore(String receiptInfo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<> (201,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        List<Cart> cartList = cartMapper.selectList(new QueryWrapper<Cart>().eq("user_id",uid));
        String errorMsg = "";
        if(receiptInfo==null||receiptInfo.equals("")){
            errorMsg=errorMsg+"地址信息不能为空，请前往\"我的信息\"页面进行设置";
            return new JSONResult<>(203,errorMsg);
        }
        for (Cart cart : cartList){
            Goods goods = goodsMapper.selectById(cart.getGoodsId());
            if (goods.getGoodsStore()<cart.getNums()){
                errorMsg = errorMsg+goods.getGoodsName()+"的库存不足"+'\n';
            }
        }
        if(!errorMsg.equals("")){
            return new JSONResult<>(202,errorMsg);
        }
        return new JSONResult<>(200,"库存充足");
    }
}
