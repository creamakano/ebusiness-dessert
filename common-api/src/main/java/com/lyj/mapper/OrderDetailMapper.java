package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    List<OrderDetail> selectOrderDetailByOrderId(@Param("orderId") Integer orderId);
    @Select("select * from tb_orderDetail where order_id = #{orderId} ")
    List<OrderDetail> selectAllByOrderId(@Param("orderId") Integer orderId);
}
