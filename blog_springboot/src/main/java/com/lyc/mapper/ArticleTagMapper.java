package com.lyc.mapper;

import com.lyc.model.po.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_article_tag】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.ArticleTag
*/
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    void saveBatchArticleTag(@Param("articleId") Integer articleId,@Param("existTagIdList") List<Integer> existTagIdList);
}




