package com.lyj.controller.reception;

import com.lyj.utils.CodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CodeController {
    //生成的code 是通过流的 方式回显到img标签中的
    @GetMapping("/getCode")
    public void getCode(HttpServletRequest request , HttpServletResponse response){
        CodeUtils.getvalidateCode(request,response);
    }
}
