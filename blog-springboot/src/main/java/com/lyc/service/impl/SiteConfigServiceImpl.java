package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.constant.CommonConstant;
import com.lyc.mapper.BlogFileMapper;
import com.lyc.model.po.BlogFile;
import com.lyc.model.po.SiteConfig;
import com.lyc.service.RedisService;
import com.lyc.service.SiteConfigService;
import com.lyc.mapper.SiteConfigMapper;
import com.lyc.strategy.context.UploadStrategyContext;
import com.lyc.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

import static com.lyc.constant.CommonConstant.UPLOAD_FAIL;
import static com.lyc.constant.RedisConstant.SITE_SETTING;
import static com.lyc.enums.FilePathEnum.CONFIG;

/**
* @author 蜡笔
* @description 针对表【t_site_config】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService{

    @Autowired
    private RedisService redisService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Resource
    private SiteConfigMapper siteConfigMapper;

    @Resource
    private BlogFileMapper blogFileMapper;

    @Override
    public SiteConfig getSiteConfig() {
        //redisService.deleteObject(SITE_SETTING);
        SiteConfig siteConfig = redisService.getObject(SITE_SETTING);
        if (Objects.isNull(siteConfig)) {
            // 从数据库中加载
            siteConfig = siteConfigMapper.selectById(1);
            redisService.setObject(SITE_SETTING, siteConfig);
        }
        return siteConfig;
    }

    @Override
    public void updateSiteConfig(SiteConfig siteConfig) {
        //根据id更新网站配置
        siteConfigMapper.updateById(siteConfig);
        //从缓存中删除信息
        redisService.deleteObject(SITE_SETTING);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        //上传文件
        String url= uploadStrategyContext.executeUploadStrategy(file,CONFIG.getPath());

        //获取文件md5信息，作为文件名
        //md5为计算机安全领域广泛使用的一种散列函数，用以提供消息的完整性保护
        String md5 = FileUtils.getMd5(file);
        //从数据库中查找文件
        BlogFile blogFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                .select(BlogFile::getId)
                .eq(BlogFile::getFilePath, CONFIG.getPath())
                .eq(BlogFile::getFileName, md5)

        );
        //文件不存在，封装对象插入数据库
        if(Objects.isNull(blogFile)){
            BlogFile newBlogFile = BlogFile.builder()
                    .fileName(md5)
                    .filePath(CONFIG.getPath())
                    .fileSize((int)file.getSize())
                    .fileUrl(url)
                    .extendName(FileUtils.getExtension(file))
                    .isDir(CommonConstant.FALSE)
                    .build();
            int count=blogFileMapper.insert(newBlogFile);
            //文件上传失败
            if(count==0){
                throw new RuntimeException(UPLOAD_FAIL);
            }
        }
        return url;
    }
}




