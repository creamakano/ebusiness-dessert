package com.lyj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//开启链式结构
@TableName("t_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    @TableField("user_email")
    private String userEmail;
    private String password;
    private String tel;
    @TableField("create_user")
    private String createUser;
    @TableField("modify_user")
    private String modifyUser;
    @TableField("create_time")
    private Date createTime;
    @TableField("modify_time")
    private Date modifyTime;
    @TableField("is_delete")
    private Integer isDelete;//0 表示已被删除，1表示未被删除
    private String salt;
}
