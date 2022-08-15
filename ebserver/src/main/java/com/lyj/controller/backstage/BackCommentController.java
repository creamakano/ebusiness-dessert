package com.lyj.controller.backstage;

import com.lyj.service.CommentService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("comment")
public class BackCommentController {
    public static final Integer COMMENTPAGESIZE = 6;//页面大小

    @Autowired
    private CommentService commentService;

    @GetMapping("selectComment")
    public String selectCommentByPage(Integer currentPage ,  Model model){
        return commentService.selectCommentByPage(currentPage,COMMENTPAGESIZE,model);
    }

    @PostMapping("deleteComment")
    @ResponseBody
    public JSONResult<Void> deleteComment(Integer commentId){
        return commentService.deleteComment(commentId);
    }
}
