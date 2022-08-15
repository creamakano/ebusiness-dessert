package com.lyj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer commentId;
    private Integer orderId;
    private Integer goodsId;
    private String content;
    private Date createDate;
    @TableField(exist = false)
    private User user;
}
