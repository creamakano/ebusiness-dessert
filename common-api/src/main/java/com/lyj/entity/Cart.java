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
@Accessors(chain = true)
@TableName("t_cart")
public class Cart {
    @TableId(type = IdType.AUTO)
    private Integer cartId;
    private Integer userId;
    private Integer goodsId;
    private Integer nums;//商品数量
    @TableField(exist = false)
    private Goods goods;
}
