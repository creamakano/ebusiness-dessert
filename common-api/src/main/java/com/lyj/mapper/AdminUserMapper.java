package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminUserMapper extends BaseMapper<AdminUser> {
    @Select("select * from t_adminUser limit #{index} , #{size}")
    List<AdminUser> selectAdminUserByPage(@Param("index") Integer index ,@Param("size") Integer size);
}
