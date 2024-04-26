package com.lyc.service;

import com.lyc.model.po.SiteConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 蜡笔
* @description 针对表【t_site_config】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface SiteConfigService extends IService<SiteConfig> {

    /**
     * 获取网站配置
     * @return rrr
     */
    SiteConfig getSiteConfig();

    /**
     * 更新网站配置
     * @param siteConfig 传入的配置
     */
    void updateSiteConfig(SiteConfig siteConfig);

    /**
     * 更新图片
     * @param file 图片资源
     * @return rr
     */
    String uploadFile(MultipartFile file);
}
