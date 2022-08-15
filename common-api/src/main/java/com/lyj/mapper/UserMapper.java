package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from t_user limit #{index} , #{size} ")
    List<User> selectUserByPage(@Param("index") Integer index , @Param("size") Integer size);
}
