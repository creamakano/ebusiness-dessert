package com.lyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.entity.Goods;
import com.lyj.entity.GoodsFocus;

import java.util.List;

public interface FocusMapper extends BaseMapper<GoodsFocus> {
    List<GoodsFocus> selectMyFocus(int uId);
    List<Goods> getMyFocusGoodsInfo(int uid);
}
