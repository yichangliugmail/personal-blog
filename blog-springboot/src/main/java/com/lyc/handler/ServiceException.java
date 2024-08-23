package com.lyc.handler;

import lombok.Getter;

/**
 * @author liuYichang
 * @description 处理业务异常
 * @date 2023/4/24 21:54
 */
@Getter
public final class ServiceException extends RuntimeException{

    /**
     * 错误信息
     */
    private final String errorMsg;


    public ServiceException(String errorMsg) {
        this.errorMsg=errorMsg;
    }
}
