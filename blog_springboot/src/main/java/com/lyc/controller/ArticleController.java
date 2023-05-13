package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.annotation.VisitLogger;
import com.lyc.common.Result;
import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.vo.ArticleBackVO;
import com.lyc.common.PageResult;
import com.lyc.model.vo.ArticleHomeVO;
import com.lyc.model.vo.ArticleRecommendVO;
import com.lyc.model.vo.ArticleVO;
import com.lyc.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.lyc.enums.StatusCodeEnum.SUCCESS;


/**
 * @author 刘怡畅
 * @description 博客文章控制器
 * @date 2023/4/27 16:49
 */

@Api(value = "博客文章控制器",tags = "文章模块")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取后台文章集合")
    @SaCheckPermission("blog:article:list")
    @GetMapping("/admin/article/list")
    public Result<PageResult<ArticleBackVO>> getBackArticleList(ConditionDTO conditionDTO){
        PageResult<ArticleBackVO> articleLists=articleService.getBackArticleList(conditionDTO);
        return Result.success(articleLists);
    }

    @ApiOperation("上传文章风封面图片")
    @SaCheckPermission("blog:article:upload")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/article/upload")
    public Result<String> upArticlePicture(@Param("file") MultipartFile file){
        String url=articleService.uploadPicture(file);
        return Result.success(url);
    }

    @ApiOperation("发布文章")
    @SaCheckPermission("blog:article:add")
    @PostMapping("/admin/article/add")
    public Result<String> addArticle(@RequestBody ArticleDTO articleDTO){
        articleService.addArticle(articleDTO);
        return Result.success(null);
    }


    @VisitLogger(value = "首页")
    @ApiOperation(value = "查看首页文章列表")
    @GetMapping("/article/list")
    public Result<PageResult<ArticleHomeVO>> listArticleHomeVO() {
        return Result.success(articleService.listArticleHomeVO());
    }

    @ApiOperation("推荐文章")
    @GetMapping("/article/recommend")
    public Result<List<ArticleRecommendVO>> recommendArticle() {
        return Result.success(articleService.listArticleRecommendVO());
    }

    @ApiOperation("查看文章详情")
    @GetMapping("/article/{articleId}")
    public Result<ArticleVO> detailArticle(@PathVariable Integer articleId){
        return Result.success(articleService.getArticleDetail(articleId));
    }


}
