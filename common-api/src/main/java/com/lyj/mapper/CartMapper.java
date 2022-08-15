package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper extends BaseMapper<Cart> {
    List<Cart> selectCartInfoByUid(@Param("uid") Integer uid);
}
