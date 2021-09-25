package com.xxxx.store.controller;

//import com.xxxx.store.controller.ex.*;

import com.xxxx.store.service.exception.*;
import com.xxxx.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 控制器类的基类 */
public class BaseController {
    /** 操作成功的状态码 */
    public static final int OK = 200;

    /** @ExceptionHandler用于统一处理方法抛出的异常 */
    @ExceptionHandler({ServiceException.class, FileException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<Void>(e);
        if(e instanceof UserException){
            result.setState(301);
            result.setMessage(e.getMessage());
        }else if (e instanceof PassWordException) {
            result.setState(302);
            result.setMessage(e.getMessage());
        }else if(e instanceof UnknownException){
            result.setState(505);
            result.setMessage(e.getMessage());
        }else if (e instanceof TokenException){
            result.setState(303);
            result.setMessage(e.getMessage());
        }else if(e instanceof FileException){
            result.setState(303);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}
