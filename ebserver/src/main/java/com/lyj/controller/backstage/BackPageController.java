package com.lyj.controller.backstage;

import com.lyj.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("back")
public class BackPageController {

    @Autowired
    public GoodsService goodsService;

    @RequestMapping("toAdminLogin")
    public String toAdminLogin(Model model){
        model.addAttribute("errorMessage","");
        return "backstage/login";
    }
    @GetMapping("toAddGoodsType")
    public String toAddGoodsType(){
        return "backstage/addGoodsType";
    }
    @GetMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("adminUser");
        return "redirect:/index";
    }

    @GetMapping("toAddGoods")
    public String toAddGoods (Model model){
        return goodsService.toAddGoods(model);
    }

}
