package com.lyj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.Goods;
import com.lyj.entity.GoodsFocus;
import com.lyj.entity.User;
import com.lyj.mapper.FocusMapper;
import com.lyj.service.FocusService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FocusServiceImpl implements FocusService {
    @Autowired(required = false)
    private FocusMapper focusMapper;

    @Override
    public JSONResult<Void> focus(Integer goodId, HttpSession session) {
        if(goodId==null){
            return new JSONResult<>(201,"该商品可能被删除了");
        }
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<>(202,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        GoodsFocus goodsFocus = focusMapper.selectOne(new QueryWrapper<GoodsFocus>().eq("goods_id",goodId).eq("user_id",uid));
        if (goodsFocus!=null){
            return new JSONResult<>(203,"该商品已被收藏");
        }
        GoodsFocus goodsFocus2 = new GoodsFocus().setGoodsId(goodId)
                .setUserId(uid).setFocusTime(new Date());
        int row = focusMapper.insert(goodsFocus2);
        if(row!=1){
            return new JSONResult<>(204,"收藏失败，稍后重试");
        }
        return new JSONResult<>(200,"收藏成功");
    }

    @Override
    public String selectMyFocus(Model model,HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<GoodsFocus> goodsFocusList = focusMapper.selectMyFocus(user.getUserId());
        model.addAttribute("myFocus",goodsFocusList);
        return "reception/myFocus";
    }

    @Override
    public String myFocus(HttpSession session, Model model) {
        User user =(User) session.getAttribute("user");
        if(user==null){
            model.addAttribute("errorMsg","会话超时，请重新登录");
            return "reception/error";
        }
        List<Goods> focusList = focusMapper.getMyFocusGoodsInfo(user.getUserId());
        model.addAttribute( "focusList" ,focusList);
        return "reception/myFocus2";
    }

    @Override
    public JSONResult<List<Goods>> getMyFocusInfo(HttpSession session) {
        User user =(User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<>(201,"会话超时");
        }

        List<Goods> list = focusMapper.getMyFocusGoodsInfo(user.getUserId());
        return new JSONResult<>(200,"Ok",list);
    }

    @Override
    public String deleteFocus(Integer id) {
        focusMapper.delete(new QueryWrapper<GoodsFocus>().eq("goods_id",id));
        return "redirect:/myFocus";
    }
}
