package com.lyj.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//为了统一返回前端的JSON格式
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONResult<T>{//类泛型
    private Integer code;//编码 200 成功 非200 失败
    private String message;
    private T data;

    public JSONResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
