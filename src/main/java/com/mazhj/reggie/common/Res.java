package com.mazhj.reggie.common;

import lombok.Data;

@Data
public class Res<T> {
    private Integer code;  //编码：1成功，0和其它数字为失败
    private String message; //错误信息
    private T data; // 响应数据

    /**
     * success
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Res<T> success(T data){
        Res<T> tRes = new Res<>();
        tRes.code = 1;
        tRes.data = data;
        return tRes;
    }

    /**
     * fail
     * @param message
     * @return
     * @param <T>
     */
    public static <T> Res<T> error(String message){
        Res<T> tRes = new Res<>();
        tRes.code = 0;
        tRes.message = message;
        return tRes;
    }
}
