package com.lyc.service;

import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.ArticleBackVO;
import com.lyc.common.PageResult;
import com.lyc.model.vo.ArticleHomeVO;
import com.lyc.model.vo.ArticleRecommendVO;
import com.lyc.model.vo.ArticleVO;
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
     * @return rr
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
     * @return rr
     */
    PageResult<ArticleHomeVO> listArticleHomeVO();

    /**
     * 获取置顶文章
     * @return rr
     */
    List<ArticleRecommendVO> listArticleRecommendVO();

    /**
     * 查看文章详情
     * @param articleId 文章ID
     * @return rr
     */
    ArticleVO getArticleDetail(Integer articleId);
}
