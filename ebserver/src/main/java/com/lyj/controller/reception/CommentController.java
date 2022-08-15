package com.lyj.controller.reception;

import com.lyj.service.CommentService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("addComment")
    @ResponseBody
    public JSONResult<Void> addComment(Integer orderId, Integer goodsId, String content){
        return commentService.addComment(orderId,goodsId,content);
    }


}
