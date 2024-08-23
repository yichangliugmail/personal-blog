package com.lyc.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author liuYichang
 * @description 文件md5工具类
 * @date 2023/4/30 10:19
 */
@Log4j2
public class FileUtils {

    /**
     * 获取文件的MD5数据
     * @param file 文件
     * @return md5字符串
     */
    public static String getMd5(MultipartFile file){
        String md5=null;
        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            md5 = DigestUtils.md5DigestAsHex(inputStream);
        }catch (Exception e){
            log.error("文件不存在");

        }

        return md5;
    }

    /**
     * 获取文件名的后缀（文件类型）
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            //文件类型png,jpg...
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 转换file
     *
     * @param multipartFile 文件
     * @return {@link File} 临时文件
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File tempFile = null;
        try {
            // 获取文件md5值
            String md5 = getMd5(multipartFile);
            // 获取文件扩展名
            String extName = getExtension(multipartFile);
            // 重新生成文件名
            String fileName = md5 + extName;
            // 创建临时文件
            tempFile = File.createTempFile(fileName, extName);
            multipartFile.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    /**
     * 创建目录
     *
     * @param file 文件
     * @return 是否创建成功
     */
    public static boolean mkdir(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return false;
        }
        return file.mkdirs();
    }

    /**
     * 删除文件
     *
     * @param src 文件
     */
    public static void deleteFile(File src) {
        for (File file : src.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteFile(file);
            }
        }
        src.delete();
    }
}
