package com.lyc.service;

import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.RecommendDTO;
import com.lyc.model.dto.TopDTO;
import com.lyc.model.po.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.*;
import com.lyc.common.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_article】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface ArticleService extends IService<Article> {

    /**
     * 获取后台文章集合
     */
    PageResult<ArticleBackVO> getBackArticleList(ConditionDTO conditionDTO);

    /**
     * 上传文章封面图片
     */
    String uploadPicture(MultipartFile file);

    /**
     * 发布文章
     */
    void addArticle(ArticleDTO articleDTO);

    /**
     * 获取前台文章集合
     */
    PageResult<ArticleHomeVO> listArticleHomeVO();

    /**
     * 获取置顶文章
     */
    List<ArticleRecommendVO> listArticleRecommendVO();

    /**
     * 查看文章详情
     */
    ArticleVO getArticleDetail(Integer articleId);

    /**
     * 文章置顶
     */
    void changeTop(TopDTO topDTO);

    /**
     * 获取文章数据
     */
    ArticleInfoVO getArticleInfo(Integer articleId);

    /**
     * 更新文章
     */
    void updateArticle(ArticleDTO articleDTO);

    /**
     * 文章推荐
     */
    void recommendArticle(RecommendDTO recommend);

    /**
     * 根据关键字查询文章
     */
    List<ArticleSearchVO> searchArticleVO(String keywords);
}
