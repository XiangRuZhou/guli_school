package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常处理类
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor //有参构造
public class EduTeacherException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer errorCode;
    @ApiModelProperty(value = "错误信息")
    private String msg;
}
