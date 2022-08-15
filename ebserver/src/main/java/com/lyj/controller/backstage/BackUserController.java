package com.lyj.controller.backstage;

import com.lyj.entity.AdminUser;
import com.lyj.service.UserService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("back")
public class BackUserController {
    private static final Integer USERPAGESIZE = 6;//页面大小

    @Autowired(required = false)
    private UserService userService;


    @RequestMapping("login")
    public String Login(AdminUser user, Model model , HttpSession session){
        return userService.AdminLogin(user,model,session);
    }

    @GetMapping("selectUser")
    public String selectUserByPage(Integer currentPage ,Model model){
        return userService.selectUserByPage(currentPage,USERPAGESIZE,model);
    }
    @GetMapping("deleteUser")
    @ResponseBody
    public JSONResult<Void> deleteUser(Integer userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("selectAdminUser")
    public String selectAdminUserByPage(Integer currentPage ,Model model){
        return userService.selectAdminUserByPage(currentPage,USERPAGESIZE,model);
    }

    @PostMapping("selectOneAdminUserById")
    @ResponseBody
    public JSONResult<AdminUser> selectOneAdminUserById(Integer id){
        return userService.selectOneAdminUserById(id);
    }
    @PostMapping("setStatus")
    @ResponseBody
    public JSONResult<Void> setStatus(Integer id,Integer status,HttpSession session){
        return userService.setStatus(id,status,session);
    }

    @PostMapping("addOrUpdAdminUser")
    @ResponseBody
    public JSONResult<Void> addOrUpdAdminUser(String act ,String userName,String password , Integer isSuper , Double salary,Integer key){
        return userService.addOrUpdAdminUser(act,userName,password,isSuper,salary,key);
    }


}
