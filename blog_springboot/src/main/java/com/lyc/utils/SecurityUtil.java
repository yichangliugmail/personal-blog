package com.lyc.utils;

import cn.dev33.satoken.secure.SaSecureUtil;

/**
 * @author 刘怡畅
 * @description 用户密码工具类
 * @date 2023/4/24 21:26
 */

public class SecurityUtil {

    /**
     * 密码校验
     * @param origin 原密码
     * @param target 数据库中的密码
     * @return 是否正确
     */
    public static boolean checkPW(String origin,String target){
        String str = sha256Encrypt(origin);
        return str.equals(target);
    }

    /**
     * 密码加密
     * @param passwd 原密码
     * @return 加密后密码
     */
    public static String sha256Encrypt(String passwd){
        return SaSecureUtil.sha256(passwd);
    }
}
