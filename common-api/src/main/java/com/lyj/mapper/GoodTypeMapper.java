package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.GoodType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodTypeMapper extends BaseMapper<GoodType> {
    List<GoodType> selectGoodsTypeByPage(
            @Param("index") Integer index , @Param("size") Integer size);
    @Select("select count(*) from t_goodsType")
    Integer countAll();
}
