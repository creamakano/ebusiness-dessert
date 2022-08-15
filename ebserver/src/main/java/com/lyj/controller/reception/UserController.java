package com.lyj.controller.reception;

import com.lyj.service.UserService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @PostMapping("checkEmail")
    @ResponseBody//将指定类型的数据转成JSON 返回给浏览器
    public JSONResult<Void> checkEmail(String email){
        return userService.checkEmailService(email);
    }

    @PostMapping("checkPwd")
    @ResponseBody
    public JSONResult<Void> checkPwd(String password , String confirmPwd){
        //密码的非空判断和一致性判断
        return userService.checkPasswordAndConfirmPwd(password,confirmPwd);
    }
    //验证码
    @ResponseBody
    @PostMapping("sendEmail")
    public JSONResult<Void> sendEmail(String email, HttpSession session){
        return userService.sendEmail(email,session);
    }

    @ResponseBody
    @PostMapping("doReg")
    public JSONResult<Void> doReg(String email,String password, String confirmPwd, String myCode,HttpSession session){
        return userService.deReg(email,password,confirmPwd,myCode,session);
    }

    @ResponseBody
    @PostMapping("checkLoginEmail")
    public JSONResult<Void> checkLoginEmail(String email){
        return userService.checkLoginEmail(email);
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public JSONResult<Void> doLogin(String email, String password, String code,HttpSession session){
        return userService.doLogin(email,password,code,session);
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public JSONResult<Void> updatePwd(String password,HttpSession session){
        return userService.updatePwd(password,session);
    }



}
