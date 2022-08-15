package com.lyj.service;

import com.lyj.utils.JSONResult;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface CountService {
    String toCount(HttpSession session, Model model);

    JSONResult<Void> checkCount(HttpSession session);

    JSONResult<Void> checkStore(String receiptInfo, HttpSession session);
}
