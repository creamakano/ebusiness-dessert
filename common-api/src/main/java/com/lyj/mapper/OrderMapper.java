package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    List<Order> selectOrderByPage(@Param("index") Integer index , @Param("size") Integer size);
    @Select("select count(*) from tb_order")
    Integer count();

    List<Order> selectAllOrderWithAddressInfoByUid(@Param("uid") Integer uid );
}
