package com.atguigu.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 */
@Data
public class ResultMsg {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private ResultMsg(){};

    public static ResultMsg ok(){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(true);
        resultMsg.setCode(ResultCode.SUCCESS);
        resultMsg.setMessage("成功");
        return resultMsg;
    }

    public static ResultMsg error(){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccess(false);
        resultMsg.setCode(ResultCode.ERROR);
        resultMsg.setMessage("失败");
        return resultMsg;
    }

    public ResultMsg success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultMsg message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultMsg code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultMsg data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultMsg data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
