package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.config.annotation.AccessLimit;
import com.lyc.config.annotation.VisitLogger;
import com.lyc.model.common.Result;
import com.lyc.constant.enums.LikeTypeEnum;
import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.RecommendDTO;
import com.lyc.model.dto.TopDTO;
import com.lyc.model.vo.*;
import com.lyc.model.common.PageResult;
import com.lyc.service.ArticleService;
import com.lyc.strategy.context.LikeStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    private LikeStrategyContext likeStrategyContext;

    @ApiOperation("后台文章列表")
    @SaCheckPermission("blog:article:list")
    @GetMapping("/admin/article/list")
    public Result<PageResult<ArticleBackVO>> getBackArticleList(ConditionDTO conditionDTO){
        PageResult<ArticleBackVO> articleLists=articleService.getBackArticleList(conditionDTO);
        return Result.success(articleLists);
    }

    @ApiOperation("上传文章封面图片")
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
    @ApiOperation(value = "首页文章列表")
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

    @ApiOperation("文章置顶")
    @PutMapping("/admin/article/top")
    public Result<?> articleTop(@RequestBody TopDTO topDTO){
        articleService.changeTop(topDTO);
        return Result.success(null);
    }

    @ApiOperation("获取文章数据用于编辑")
    @SaCheckPermission("blog:article:edit")
    @GetMapping("/admin/article/edit/{articleId}")
    public Result<ArticleInfoVO> getArticleInfo(@PathVariable Integer articleId){
        return Result.success(articleService.getArticleInfo(articleId));
    }

    @ApiOperation("修改文章")
    @PutMapping("/admin/article/update")
    public Result<?> updateArticle(@RequestBody ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);
        return Result.success(null);
    }

    @ApiOperation("点赞文章")
    @AccessLimit(seconds = 60,maxCount = 3)
    @SaCheckLogin
    @PostMapping("/article/{articleId}/like")
    public Result<Object> likeArticle(@PathVariable("articleId") Integer articleId){
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.ARTICLE,articleId);
        return Result.success(null);
    }

    @ApiOperation("文章推荐")
    @PutMapping("/admin/article/recommend")
    @SaCheckLogin
    public Result<?> recommendArticle(@RequestBody RecommendDTO recommendDTO){
        articleService.recommendArticle(recommendDTO);
        return Result.success(null);
    }

    @ApiOperation("文章查询")
    @GetMapping("/article/search")
    public Result<List<ArticleSearchVO>> recommendArticle(String keywords){
        List<ArticleSearchVO> list = articleService.searchArticleVO(keywords);
        return Result.success(list);
    }

}
