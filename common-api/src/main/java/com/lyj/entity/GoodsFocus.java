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
@TableName("t_focus")
public class GoodsFocus {
    @TableId(type = IdType.AUTO)
    private Integer focusId;
    private Integer goodsId;
    private Integer userId;
    private Date focusTime;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Goods goods;
}
