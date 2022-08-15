package com.lyj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.*;
import com.lyj.mapper.*;
import com.lyj.service.UserService;
import com.lyj.utils.Email;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private AdminUserMapper adminUserMapper;
    @Autowired(required = false)
    private AddressMapper addressMapper;
    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private FocusMapper focusMapper;
    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Override
    public JSONResult<Void> checkEmailService(String email) {
        if ("".equals(email) || email == null) {
            return new JSONResult<>(201, "邮箱不能为空");
        }
        //判断邮箱的格式是否正确 qq邮箱
        String regex = "[1-9]\\d{7,10}@qq\\.com";
        if (!email.matches(regex)) {
            return new JSONResult<>(202, "邮箱格式错误");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_email", email));
        if (user != null) {
            return new JSONResult<>(203, "该邮箱已被注册");
        }
        return new JSONResult<>(200, "邮箱验证通过");

    }

    @Override
    public JSONResult<Void> checkPasswordAndConfirmPwd(String password, String confirmPwd) {
        //非空判断
        if ("".equals(password) || password == null) {
            return new JSONResult<>(201, "密码不能为空");
        }
        if ("".equals(confirmPwd) || confirmPwd == null) {
            return new JSONResult<>(202, "确认密码不能为空");
        }
        //一致性判断
        if (!password.equals(confirmPwd)) {
            return new JSONResult<>(203, "两次密码输入不一致");
        }
        return new JSONResult<>(200, "OK");
    }

    @Override
    public JSONResult<Void> sendEmail(String email, HttpSession session) {
        //参数判断
        if("".equals(email)||email==null){
            return new JSONResult<>(201,"邮箱不能为空");
        }
        String code = Email.sendEmail(email);
        if(code==null){
            return new JSONResult<>(202,"验证码发送失败");
        }
        session.setAttribute("code",code);
        return new JSONResult<>(200,"验证成功");
    }

    @Override
    public JSONResult<Void> deReg(String email, String password, String confirmPwd, String myCode, HttpSession session) {
        JSONResult<Void> result = checkEmailService(email);
        if(result.getCode()!=200){
            return result;
        }
        JSONResult<Void> result1 = checkPasswordAndConfirmPwd(password,confirmPwd);
        if(result.getCode()!=200){
            return result1;
        }

        if(myCode==null||"".equals(myCode)){
            return new JSONResult<>(204,"邮箱验证码必填");
        }
        String code = (String) session.getAttribute("code");
        if(code==null||code.equals("")){
            return new JSONResult<>(205,"验证码无效");
        }
        if(!code.equals(myCode)){
            return new JSONResult<>(205,"邮箱验证码输入错误");
        }
        // 密码加密 生成盐
        //可以是任意字符串
        String salt = UUID.randomUUID().toString();//36位字符串
        String md5Password1 = md5Password(password, salt);

        //封装数据到对象中
        User user = new User()
                .setUserEmail(email).setPassword(md5Password1)
                .setSalt(salt).setIsDelete(1)
                .setCreateTime(new Date());

        int row = userMapper.insert(user);
        if(row!=1){
            return new JSONResult<>(207,"注册失败，请稍后重试");
        }
        return new JSONResult<>(200,"注册成功");


    }

    @Override
    public JSONResult<Void> checkLoginEmail(String email) {
        if(email==null||"".equals(email)){
            return new JSONResult<>(201,"登录邮箱必填");
        }
        //格式验证
        String regex = "[1-9]\\d{7,10}@qq\\.com";
        if (!email.matches(regex)) {
            return new JSONResult<>(202, "登录邮箱格式错误");
        }
        //是否注册判断
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_email", email));
        if (user == null) {
            return new JSONResult<>(203, "登录邮箱未注册，请先注册");
        }
        return new JSONResult<>(200, "OK");
    }

    @Override
    public JSONResult<Void> doLogin(String email, String password, String code, HttpSession session) {
        JSONResult<Void> result = checkLoginEmail(email);
        if(result.getCode()!=200){
            return result;
        }
        //检查密码
        if (password==null||"".equals(password)){
            return new JSONResult<>(501,"登录密码必填");
        }
        //根据email查询当前数据
        User user =userMapper.selectOne(new QueryWrapper<User>().eq("user_email",email));
        if(user==null){
            return new JSONResult<>(502,"该数据可能被删除了");
        }

        //获取注册时加密后的密码和注册时使用的盐
        String regMD5Pwd = user.getPassword();
        String salt = user.getSalt();
        //检查密码是否正确
        String md5Password = md5Password(password,salt);
        if(!regMD5Pwd.equals(md5Password)){
            return new JSONResult<>(503,"密码错误");
        }
        //todo 暂时关掉验证码的验证
        //判断code值
        if(code==null||code.equals("")){
            return new JSONResult<>(504,"验证码不能为空");
        }
        // 对比
        String loginCode = (String) session.getAttribute("loginCode");
        if(loginCode==null){
            return new JSONResult<>(505,"验证码失效");
        }
        if(!loginCode.equals(code.toUpperCase())){
            return new JSONResult<>(506,"验证码错误");
        }
        // 数据绑定
        session.setAttribute("user",user);
        return new JSONResult<>(200,"登录成功");
    }

    @Override
    public JSONResult<Void> updatePwd(String password, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JSONResult<>(201,"会话超时，请重新登录");
        }
        Integer uid = user.getUserId();
        if(password==null||password.equals("")){
            return new JSONResult<>(202,"密码不能为空");
        }

        String md5Password = md5Password(password,user.getSalt());
        user.setPassword(md5Password);
        int row = userMapper.updateById(user);
        if(row!=1){
            return new JSONResult<>(203,"更改密码失败");
        }
        session.setAttribute("user",user);
        return new JSONResult<>(200,"更改密码成功");
    }

    //封装一个方法用于密码加密使用
    private String md5Password(String password, String salt){
        String md5Pwd = password+salt;
        //调用api加密
        return   DigestUtils.md5DigestAsHex(md5Pwd.getBytes());
    }




    //------------------------------------------------------------------------------------------
    //后台服务



    @Override
    public JSONResult<Void> deleteUser(Integer uid) {
        addressMapper.delete(new QueryWrapper<Address>().eq("user_id",uid));
        cartMapper.delete(new QueryWrapper<Cart>().eq("user_id",uid));
        focusMapper.delete(new QueryWrapper<GoodsFocus>().eq("user_id",uid));
        orderMapper.delete(new QueryWrapper<Order>().eq("user_id",uid));
        int row = userMapper.deleteById(uid);
        if (row != 1) {
            return new JSONResult<>(201, "删除失败");
        }
        return new JSONResult<>(200, "删除成功");
    }
    @Override
    public String AdminLogin(AdminUser adminUser, Model model, HttpSession session) {
        //验证账户密码是否正确
        AdminUser adminUser1 = adminUserMapper.selectOne(new QueryWrapper<AdminUser>()
                .eq("user_name", adminUser.getUserName())
                .eq("password", adminUser.getPassword())
                .eq("status", 1));

        if (adminUser1 == null) {
            model.addAttribute("errorMessage", 1);
            return "backstage/login";
        }
        session.setAttribute("adminUser", adminUser1);
        return "redirect:/goods/selectAllTypeByPage?currentPage=1";
    }

    @Override
    public String selectUserByPage(Integer page, Integer size, Model model) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer index = (page - 1) * size;
        List<User> userList = userMapper.selectUserByPage(index, size);
        Integer nums = userMapper.selectList(new QueryWrapper<>()).size();
        Integer totalPage = (int) Math.ceil((double) nums / (double) size);
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "backstage/allUser";
    }

    @Override
    public String selectAdminUserByPage(Integer page, Integer size, Model model) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer index = (page - 1) * size;
        List<AdminUser> adminUserList = adminUserMapper.selectAdminUserByPage(index, size);
        Integer nums = adminUserMapper.selectList(new QueryWrapper<>()).size();
        Integer totalPage = (int) Math.ceil((double) nums / (double) size);
        model.addAttribute("adminUserList", adminUserList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "backstage/adminUser";
    }

    @Override
    public JSONResult<AdminUser> selectOneAdminUserById(Integer id) {
        if (id == null || id == 0) {
            return new JSONResult<>(201, "id为空");
        }
        AdminUser adminUser = adminUserMapper.selectById(id);
        return new JSONResult<>(200, "查找成功", adminUser);
    }

    @Override
    public JSONResult<Void> setStatus(Integer id, Integer status, HttpSession session) {
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
        if (adminUser != null && adminUser.getId() == id) {
            return new JSONResult<Void>(201, "操作失败，请勿对自己的账号进行操作");
        }
        AdminUser adminUser1 = adminUserMapper.selectById(id);
        adminUser1.setStatus(status);
        int row = adminUserMapper.updateById(adminUser1);
        if (row != 1) {
            return new JSONResult<>(202, "操作失败");
        }
        return new JSONResult<>(200, "操作成功");

    }

    @Override
    public JSONResult<Void> addOrUpdAdminUser(String act ,String userName,String password , Integer isSuper , Double salary,Integer key){

        if (userName == null || userName.equals("")) {
            return new JSONResult<>(204, "用户名不能为空");
        }if (password == null || password.equals("")) {
            return new JSONResult<>(201, "密码不能为空");
        }
        if (salary ==null ) {
            salary=0.0;
        }

        if (act.equals("setting")) {
            if (adminUserMapper.selectOne(new QueryWrapper<AdminUser>()
                    .eq("user_name", userName)) != null
                    && !adminUserMapper.selectById(key).getUserName().equals(userName)) {
                return new JSONResult<>(202, "用户名已存在，请重新填写");
            }
            AdminUser adminUser = new AdminUser().setId(key).setUserName(userName).setPassword(password).setIsSuper(isSuper).setSalary(salary);
            int row = adminUserMapper.updateById(adminUser);
            if (row != 1) {
                return new JSONResult<>(203, "修改失败");
            }
            return new JSONResult<>(200, "修改成功");
        } else {
            if (adminUserMapper.selectOne(new QueryWrapper<AdminUser>()
                    .eq("user_name", userName)) != null){
                return new JSONResult<>(202, "用户名已存在，请重新填写");
            }
            AdminUser adminUser = new AdminUser().setUserName(userName).setPassword(password).setIsSuper(isSuper).setSalary(salary).setStatus(1);
            int row = adminUserMapper.insert(adminUser);
            if (row != 1) {
                return new JSONResult<>(203, "添加失败");
            }
            return new JSONResult<>(200, "添加成功");
        }
    }

}
