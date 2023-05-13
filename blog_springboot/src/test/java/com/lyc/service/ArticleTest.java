package com.lyc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyc.mapper.CategoryMapper;
import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.po.Article;
import com.lyc.model.po.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 刘怡畅
 * @description 测试文章服务层
 * @date 2023/5/2 15:27
 */

@SpringBootTest
public class ArticleTest {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 使用springframework.beans.BeanUtils工具类复制对象
     */
    @Test
    void packingArticle(){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleTitle("文章标题");
        articleDTO.setArticleContent("it just a little love");
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO,article);
        System.out.println(article);
    }

    @Test
    void addArticle(){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setCategoryName("测试分类");
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, articleDTO.getCategoryName()));
        System.out.println(category.getId());
    }
}
