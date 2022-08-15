package com.lyj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_adminUser")
public class AdminUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private Integer status;
    private Integer isSuper;
    private double salary;
}
