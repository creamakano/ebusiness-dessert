package com.lyj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_orderdetail")
@Accessors(chain = true)
public class OrderDetail {
    @TableId(type = IdType.AUTO)
    @TableField("orderDetail_id")
    private Integer orderDetailId ;
    @TableField("order_id")
    private Integer OrderId;
    @TableField("goods_id")
    private Integer goodsId;
    private Integer nums;//购买的商品数量
    @TableField(exist = false)
    private Goods goods;
    @TableField(exist = false)
    private Order order;
}
