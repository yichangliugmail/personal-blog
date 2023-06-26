package com.lyc.mapper;

import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_article】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleStatisticsVO> selectArticleStatistics();

    /**
     * 条件分页查询
     * @param pageLimit 每页大小
     * @param pageSize 总页面数
     * @param condition 条件
     * @return 文章集合
     */
    List<ArticleBackVO> selectArticleBackVo(@Param("limit") Long pageLimit,@Param("size") Long pageSize, ConditionDTO condition);

    /**
     * 条件查询文章数量
     * @param condition 该注解是给参数命名，也就是#{参数}
     */
    Long ArticleCount(@Param("condition") ConditionDTO condition);

    List<ArticleHomeVO> selectArticleHomeList(@Param("limit") Long limit, @Param("size") Long size);

    /**
     * 推荐文章集合
     */
    List<ArticleRecommendVO> selectArticleRecommend();

    List<ArticleConditionVO> listArticleByCondition(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    ArticleVO selectArticleHomeById(@Param("articleId") Integer articleId);

    ArticlePaginationVO selectArticlePaginationVO(@Param("sign") String sign,@Param("articleId") Integer articleId);

    /**
     * 编辑文章信息
     */
    ArticleInfoVO selectArticleInfo(@Param("articleId") Integer articleId);

    /**
     * 根据分类名查询分类id
     */
    Integer selectArticleCategoryId(@Param("categoryName") String categoryName);

    List<ArticleSearchVO> selectByKeywords(@Param("keywords") String keywords);

}




