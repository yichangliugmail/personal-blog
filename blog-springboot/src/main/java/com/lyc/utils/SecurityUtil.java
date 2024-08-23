package com.lyc.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

/**
 * @author 刘怡畅
 * @description 加密工具类
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



    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";

    private static final String ALGORITHM_NAME = "SM4";

    /**
     * PKCS5Padding  NoPadding 补位规则，PKCS5Padding缺位补0，NoPadding不补
     */
    private static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";

    /**
     * ECB加密模式，无向量
     * @param algorithmName 算法名称
     * @param mode          模式
     * @param key           key
     * @return 结果
     */
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    /**
     * sm4加密
     * 加密模式：ECB 密文长度不固定，会随着被加密字符串长度的变化而变化
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param plainText 待加密字符串
     * @return 返回16进制的加密字符串
     */
    public static String encryptEcb(String hexKey, String plainText) {
        try {
            String cipherText;
            // 16进制字符串-->byte[]
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            // String-->byte[]
            //当加密数据为16进制字符串时使用这行
            byte[] srcData = plainText.getBytes(ENCODING);
            //声称Ecb暗号,通过第二个参数判断加密还是解密
            Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, keyData);
            // 加密后的数组
            byte[] cipherArray = cipher.doFinal(srcData);
            // byte[]-->hexString
            cipherText = ByteUtils.toHexString(cipherArray);
            return cipherText;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }


    /**
     * sm4解密
     *
     * 解密模式：采用ECB
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     */
    public static String decryptEcb(String hexKey, String cipherText) {
        try {
            // 用于接收解密后的字符串
            String decryptStr;
            // hexString-->byte[]
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            // hexString-->byte[]
            byte[] cipherData = ByteUtils.fromHexString(cipherText);
            // 解密
            byte[] srcData = decryptEcbPadding(keyData, cipherData);
            // byte[]-->String
            decryptStr = new String(srcData, ENCODING);
            return decryptStr;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    /**
     * 解密
     *
     * @param key 秘钥
     * @param cipherText 密文
     */
    public static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) {
        try {
            //生成Ecb暗号,通过第二个参数判断加密还是解密
            Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(cipherText);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    /**
     * 验证数据
     * @param hexKey key
     * @param cipherText 密文
     * @param plainText 明文
     */
    public static boolean verifyEcb(String hexKey, String cipherText, String plainText) {
        try {
            // 用于接收校验结果
            boolean flag = false;
            // hexString-->byte[]
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            // 将16进制字符串转换成数组
            byte[] cipherData = ByteUtils.fromHexString(cipherText);
            // 解密
            byte[] decryptData = decryptEcbPadding(keyData, cipherData);
            // 将原字符串转换成byte[]
            byte[] srcData = plainText.getBytes(ENCODING);
            // 判断2个数组是否一致
            flag = Arrays.equals(decryptData, srcData);
            return flag;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }


    public static void main(String[] args) {
        String text = "中文文本数据";
        System.out.println("加密前数据："+text);
        String encrypt = encryptEcb("86C63180C2806ED1F47B859DE501215B", text);
        System.out.println("加密数据:"+ encrypt);

        String decrypt = decryptEcb("86C63180C2806ED1F47B859DE501215B", encrypt);

        System.out.println("解密数据:"+decrypt);

    }

}
