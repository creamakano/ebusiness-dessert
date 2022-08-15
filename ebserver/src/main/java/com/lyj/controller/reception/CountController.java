package com.lyj.controller.reception;

import com.lyj.service.CountService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CountController {
    @Autowired
    private CountService countService;

    @GetMapping("checkCount")
    @ResponseBody
    public JSONResult<Void> checkCount(HttpSession session){
        return countService.checkCount(session);
    }

    @GetMapping("checkStore")
    @ResponseBody
    public JSONResult<Void> checkStore(String receiptInfo,HttpSession session){
        return countService.checkStore(receiptInfo,session);
    }

    @GetMapping("count")
    public String count(HttpSession session , Model model){
        return countService.toCount(session,model);
    }
}
