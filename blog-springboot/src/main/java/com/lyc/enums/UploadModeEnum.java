package com.lyc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传模式枚举
 *
 * @author 刘怡畅
 */
@Getter
@AllArgsConstructor
public enum UploadModeEnum {

    /**
     * 本地
     */
    LOCAL("local", "localUploadStrategyImpl"),

    /**
     * oss
     */
    OSS("oss", "ossUploadStrategyImpl"),

    /**
     * cos
     */
    COS("cos", "cosUploadStrategyImpl");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

    /**
     * 根据相应的上传模式，获取对应的实现策略
     *
     * @param mode 上传模式
     * @return 对应的实现策略
     */
    public static String getStrategy(String mode) {
        //根据上传模式获取相应上传策略
        for (UploadModeEnum value : UploadModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }

        //没有索索到对应的上传策略，默认上传到本地
        return LOCAL.getStrategy();
    }
}