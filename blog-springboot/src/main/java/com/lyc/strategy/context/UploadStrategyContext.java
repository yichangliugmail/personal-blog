package com.lyc.strategy.context;

import com.lyc.constant.enums.UploadModeEnum;
import com.lyc.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * @author 刘怡畅
 * @description 上传策略上下文
 * @date 2023/4/30 10:40
 */

@Service
public class UploadStrategyContext {
    /**
     * yml文件中配置的上传模式，local，cos
     */
    @Value("${upload.strategy}")
    private String uploadMode;

    /**
     * key:上传上传策略名  value：上传策略
     * UploadStrategy接口有多个实现类，无法直接注入，这里采用用Map方式获取
     */
    @Autowired
    private Map<String, UploadStrategy> uploadStrategyMap;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        //根据上传模式获取上传策略名
        String uploadStrategy= UploadModeEnum.getStrategy(uploadMode);
        //根据上传策略名获取上传策略
        UploadStrategy uploadStrategyImpl = uploadStrategyMap.get(uploadStrategy);
        //上传文件，并返回上传路径
        return uploadStrategyImpl.uploadFile(file,path);

    }
}
