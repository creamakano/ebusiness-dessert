package com.lyj.service;

import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;

public interface CommentService {

    String toComment(Integer orderId, Model model);

    JSONResult<Void> addComment(Integer orderId, Integer goodsId, String content);

    String selectCommentByPage(Integer page , Integer size , Model model);

    JSONResult<Void> deleteComment(Integer commentId);


}