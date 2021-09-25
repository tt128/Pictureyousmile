package com.xxxx.store.util;


import java.io.Serializable;


//@Repository
public class Test implements Serializable {
    Integer code;
    String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Test(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
