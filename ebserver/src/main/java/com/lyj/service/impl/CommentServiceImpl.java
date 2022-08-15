package com.lyj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.entity.Comment;
import com.lyj.entity.Order;
import com.lyj.entity.OrderDetail;
import com.lyj.mapper.CommentMapper;
import com.lyj.mapper.OrderMapper;
import com.lyj.service.CommentService;
import com.lyj.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Override
    public String toComment(Integer orderId, Model model) {
        List<OrderDetail> goodsList = commentMapper.commentGoodsList(orderId);
        model.addAttribute("goodsList",goodsList);
        return "reception/comments";
    }

    @Override
    public JSONResult<Void> addComment(Integer orderId, Integer goodsId, String content) {
        Comment comment = new Comment().setOrderId(orderId)
                .setGoodsId(goodsId).setContent(content).setCreateDate(new Date());
        int row = commentMapper.insert(comment);
        if(row!=1){
            return new JSONResult<>(203,"评价失败");
        }
        Order order =orderMapper.selectById(orderId);
        if (commentMapper.commentGoodsList(orderId).size()==0){
            order.setIsComment(1);
            orderMapper.updateById(order);
            return new JSONResult<>(200,"所有评论已完成");
        }
        order.setIsComment(2);
        orderMapper.updateById(order);
        return new JSONResult<>(201,"评价成功");
    }



    //----------------------------------------------------------

    @Override
    public String selectCommentByPage(Integer page, Integer size, Model model) {
        if (page == null || page == 0) {
            page = 1;
        }
        Integer index = (page - 1) * size;
        List<Comment> commentList = commentMapper.selectAllCommentByPage(index,size);
        Integer nums = commentMapper.selectList(new QueryWrapper<>()).size();
        Integer totalPage = (int) Math.ceil((double) nums / (double) size);
        model.addAttribute("commentList",commentList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "backstage/comments";
    }

    @Override
    public JSONResult<Void> deleteComment(Integer commentId) {
        int row = commentMapper.deleteById(commentId);
        if(row != 1){
            return new JSONResult<>(201,"删除失败");
        }
        return new JSONResult<>(200,"删除成功");
    }




}
