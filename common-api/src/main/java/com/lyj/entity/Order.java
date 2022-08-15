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
@TableName("t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer orderId;
    private Integer userId;
    private String orderName;
    private double totalPrice;
    private Integer status;// 0:未支付 ， 1：已支付
    private Integer addressId;
    private Date createDate;
    private Date modifyDate;
    private Integer isDelete; // 0：未被删除 1：已被删除
    private Integer isComment; // 0：未被删除 1：已被删除
    private String receiptInfo;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Address address;
}
