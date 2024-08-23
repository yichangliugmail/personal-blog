package com.lyc.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.lyc.constant.enums.StatusCodeEnum.*;

/**
 * @author 刘怡畅
 * @description 通用返回结果类
 * @date 2023/10/28 20:46
 */

@ApiModel(description = "返回结果")
@Data
public class Result<T> {

    @ApiModelProperty("是否成功响应")
    private Boolean flag;

    @ApiModelProperty("数据部分")
    private T data;

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String msg;


    public static <T> Result<T> success(T data){
        Result<T> r = new Result<>();
        r.setFlag(true);
        r.setData(data);
        r.setCode(SUCCESS.getCode());
        r.setMsg(SUCCESS.getMsg());
        return r;
    }

    public static <T> Result<T> success(T data,Integer code,String msg){
        Result<T> r = new Result<>();
        r.setFlag(true);
        r.setData(data);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> Result<T> fail(String msg){
        Result<T> r = new Result<>();
        r.setMsg(msg);
        r.setCode(FAIL.getCode());
        r.setFlag(false);
        r.setData(null);
        return r;
    }

    public static <T> Result<T> fail(T data,Integer code,String msg){
        Result<T> r = new Result<>();
        r.setFlag(false);
        r.setData(data);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

}
