package com.lyc.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 刘怡畅
 * @description 上传策略
 * @date 2023/4/30 10:36
 */
public interface UploadStrategy {
    /**
     * 上传文件到指定路径
     * @param file 文件
     * @param path 路径
     * @return rr
     */
    String uploadFile(MultipartFile file, String path);
}
