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
@TableName("t_address")
public class Address {
    @TableId(type = IdType.AUTO)
    private Integer addressId;
    private Integer userId;
    private String addressInfo;
    private String consigneeName;
    private String phoneNum;
    private Integer isDefault;
}

