package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.model.common.Result;
import com.lyc.model.po.SiteConfig;
import com.lyc.service.SiteConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.lyc.constant.enums.StatusCodeEnum.SUCCESS;

/**
 * @author liuYichang
 * @description
 * @date 2023/4/30 9:32
 */

@Api(value = "网站基本信息控制器",tags = "网站配置")
@RestController
public class WebSiteController {

    @Autowired
    private SiteConfigService siteConfigService;

    @ApiOperation("初始化网站基本信息")
    @SaCheckPermission("web:site:list")
    @GetMapping("/admin/site/list")
    public Result<SiteConfig> initSitConfig(){
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        return Result.success(siteConfig,SUCCESS.getCode(),SUCCESS.getMsg());
    }

    @ApiOperation("更新网站配置")
    @SaCheckPermission("web:site:update")
    @PutMapping("/admin/site/update")
    public Result<?> upWebSiteConfig(@RequestBody SiteConfig siteConfig){
        siteConfigService.updateSiteConfig(siteConfig);
        return Result.success(null,SUCCESS.getCode(),SUCCESS.getMsg());
    }

    @ApiOperation("图片资源上传")
    @SaCheckPermission("web:site:upload")
    @PostMapping("/admin/site/upload")
    public Result<String> uploadPicture(@RequestParam("file") MultipartFile file){
        String str=siteConfigService.uploadFile(file);
        return Result.success(str,SUCCESS.getCode(),SUCCESS.getMsg());
    }

}
