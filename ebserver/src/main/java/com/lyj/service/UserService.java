package com.lyj.service;


import com.lyj.entity.AdminUser;
import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface UserService {
    //检查邮箱的唯一性和格式
    JSONResult<Void> checkEmailService(String email);
    JSONResult<Void> checkPasswordAndConfirmPwd(String password , String confirmPwd);
    JSONResult<Void> sendEmail(String email , HttpSession session);
    JSONResult<Void> deReg(String email,String password, String confirmPwd, String myCode,HttpSession session);
    //检查登录邮箱的格式和是否已被注册
    JSONResult<Void> checkLoginEmail(String email);
    JSONResult<Void> doLogin(String email , String password , String code ,HttpSession session);

    /**
     * 更改用户密码
     * @param password 更改后的密码
     * @param session  用于取得用户信息和重置用户信息
     * @return
     */
    JSONResult<Void> updatePwd(String password,HttpSession session);


    //------------------------------------------------------------------------
    //后台服务

    /**
     * 管理员登录验证及跳转
     * @param adminUser
     * @param model
     * @param session
     * @return
     */
    String AdminLogin(AdminUser adminUser, Model model , HttpSession session);

    /**
     * 分页查找所有用户
     * @param page
     * @param size
     * @param model
     * @return
     */
    String selectUserByPage(Integer page , Integer size , Model model);

    /**
     * 根据用户id删除用户
     * @param uid
     * @return
     */
    JSONResult<Void> deleteUser(Integer uid);
    String selectAdminUserByPage(Integer page , Integer size , Model model);

    JSONResult<AdminUser> selectOneAdminUserById(Integer id);

    JSONResult<Void> setStatus(Integer id, Integer status, HttpSession session);

    JSONResult<Void> addOrUpdAdminUser(String act ,String userName,String password , Integer isSuper , Double salary,Integer key);



}
