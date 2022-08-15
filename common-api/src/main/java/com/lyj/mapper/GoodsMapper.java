package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {

    List<Goods> selectGoodsByPage(@Param("index") Integer index , @Param("size") Integer size);
    @Select("select count(*) from t_goods")
    Integer countAll();

    Integer updateStore(@Param("nums") Integer nums,@Param("goodsId") Integer goodsId);

    List<Goods> selectGoodsByLikeName(@Param("name") String name);
    List<Goods> selectRecommendGoodsByLikeName(@Param("name") String name);
}
