package com.lyc.controller;

import com.lyc.model.common.Result;
import com.lyc.model.vo.BlogBackInfoVO;
import com.lyc.model.vo.BlogInfoVO;
import com.lyc.service.BlogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lyc.constant.enums.StatusCodeEnum.SUCCESS;

/**
 * @author liuYichang
 * @description
 * @date 2023/4/25 17:51
 */

@Api(value = "博客基本信息控制器",tags = "博客配置模块")
@RestController
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    @ApiOperation(value = "更新访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.success(null,SUCCESS.getCode(),"访客数据");
    }

    @ApiOperation(value = "查看后台信息")
    @GetMapping("/admin")
    public Result<BlogBackInfoVO> getBlogBackInfo() {
        return Result.success(blogInfoService.getBlogBackInfo(),SUCCESS.getCode(),"博客后台信息");
    }

    @ApiOperation(value = "查看博客信息")
    @GetMapping("/")
    public Result<BlogInfoVO> getBlogInfo() {
        return Result.success(blogInfoService.getBlogInfo(),SUCCESS.getCode(),"初始化博客信息");
    }

    @ApiOperation(value = "获取首页说说")
    @GetMapping("/home/talk")
    public Result<List<String>> getHomeTalk() {
        return Result.success(new ArrayList<>());
    }

}
