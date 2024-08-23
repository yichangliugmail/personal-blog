package com.lyc.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description 加密工具
 * @Author liuYichang
 * @Date 2024/6/17 22:10
 **/
@SpringBootTest
public class SmUtilTest {
    @Test
    void Sm4Encrypt(){
        String content = "test中文uiiuhhiuuvgvgvgvj";
        method(content);
        System.out.println("------");
        method("test");
    }

    private void method(String content){
        System.out.println("加密前："+content);
        SymmetricCrypto sm4 = SmUtil.sm4("123".getBytes());

        String encryptHex = sm4.encryptHex(content);
        System.out.println("加密后："+encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密后："+decryptStr);
    }

}
