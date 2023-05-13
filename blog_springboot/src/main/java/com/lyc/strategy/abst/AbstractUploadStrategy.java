package com.lyc.strategy.abst;

import com.lyc.handler.ServiceException;
import com.lyc.strategy.UploadStrategy;
import com.lyc.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 抽象的上传模板类
 *
 * @author 刘怡畅
 */
@Service
public abstract class AbstractUploadStrategy implements UploadStrategy {

    /**
     * 实现文件上传接口，配置文件名和上传路径
     * 不负责真正的文件上传，真正的文件上传实现交给具体的实现类
     */
    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 生成文件名
            String md5 = FileUtils.getMd5(file);
            String extName = FileUtils.getExtension(file);
            String fileName = md5 + "." + extName;

            // 判断文件是否存在
            if (!exists(path + fileName)) {
                // 调用文件上传方法，由子类作具体实现
                upload(path, fileName, file.getInputStream());
            }

            // 返回文件访问路径
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文件上传失败");
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@link Boolean}
     */
    public abstract Boolean exists(String filePath);

    /**
     * 上传
     *
     * @param path        路径
     * @param fileName    文件名
     * @param inputStream 输入流
     * @throws IOException io异常
     */
    public abstract void upload(String path, String fileName, InputStream inputStream) throws IOException;

    /**
     * 获取文件访问url
     *
     * @param filePath 文件路径
     * @return {@link String} 文件url
     */
    public abstract String getFileAccessUrl(String filePath);
}
