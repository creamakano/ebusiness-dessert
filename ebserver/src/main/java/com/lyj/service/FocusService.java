package com.lyj.service;

import com.lyj.entity.Goods;
import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FocusService {
    JSONResult<Void> focus(Integer goodId, HttpSession session);

    String selectMyFocus(Model model,HttpSession session);

    String myFocus(HttpSession session,Model model);

    JSONResult<List<Goods>> getMyFocusInfo(HttpSession session);

    String deleteFocus(Integer id);
}
