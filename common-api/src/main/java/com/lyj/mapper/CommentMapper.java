package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.Comment;
import com.lyj.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    List<OrderDetail> commentGoodsList(@Param("orderId") Integer orderId);
    OrderDetail selectGoodsByOrderIdAndGoodsId(
            @Param("orderId") Integer orderId,@Param("goodsId") Integer goodsId);

    List<Comment> selectAllCommentByPage(@Param("index") Integer index , @Param("size") Integer size);

    List<Comment> selectCommentByGoodsId(@Param("goodsId") Integer goodsId);
}
