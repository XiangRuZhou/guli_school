package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMsg error(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return ResultMsg.error().message("执行了全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ResultMsg error(ArithmeticException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return ResultMsg.error().message("执行了特定异常处理");
    }

    @ExceptionHandler(EduTeacherException.class)
    @ResponseBody
    public ResultMsg error(EduTeacherException e){
        e.printStackTrace();
        log.error(e.getMsg());
        return ResultMsg.error().code(e.getErrorCode()).message(e.getMsg());
    }


}
