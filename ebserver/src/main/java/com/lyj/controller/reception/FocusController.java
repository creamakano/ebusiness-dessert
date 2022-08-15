package com.lyj.controller.reception;

import com.lyj.entity.Goods;
import com.lyj.service.FocusService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FocusController {

    @Autowired
    private FocusService focusService;

    @ResponseBody
    @GetMapping("focus")
    public JSONResult<Void> focus(Integer goodId, HttpSession session){
        return focusService.focus(goodId,session);
    }

    @GetMapping("toMyFocus")
    public String toMyFocus(Model model,HttpSession session){
        return focusService.selectMyFocus(model,session);
    }
    @GetMapping("myFocus")
    public String myFocus(Model model,HttpSession session){
        return focusService.myFocus(session,model);
    }

    //ajax请求获取我的收藏数据
    @GetMapping("getMyFocusData")
    @ResponseBody
    public JSONResult<List<Goods>> getMyFocusData(HttpSession session){
        return focusService.getMyFocusInfo(session);
    }

    @GetMapping("deleteFocus")
    public String deleteFocus(Integer id){
        return focusService.deleteFocus(id);
    }

}
