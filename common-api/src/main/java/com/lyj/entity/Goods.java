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
@TableName("t_goods")
public class Goods {
    @TableId(type = IdType.AUTO)
    private Integer id ;
    private String goodsName;
    private double goodsOrPrice;
    private double goodsRuPrice;
    private Integer goodsStore;//库存
    private String goodsPicture;
    private Integer isRecommend;
    private Integer isAdvertisement;
    private Integer goodsTypeId;
    @TableField(exist = false)
    private String typeName;
}
