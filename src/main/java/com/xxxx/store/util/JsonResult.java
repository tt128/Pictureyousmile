package com.xxxx.store.util;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 响应结果类
 * @param <E> 响应数据的类型
 */

@Repository

public class JsonResult<E> implements Serializable {
    /** 状态码 */
    private Integer state;
    /** 状态描述信息 */
    private String message;
    /** token */
    private String token;
    /** 数据 */
    private E data;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public JsonResult() {
        super();
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        super();
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state, String token, E data) {
        super();
        this.state = state;
        this.token = token;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public JsonResult(String token) {
        this.token = token;
    }
}
