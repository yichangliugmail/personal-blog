package com.lyc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyc.model.po.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author liuYichang
 * @description
 * @date 2023/5/20 16:24
 */

@SpringBootTest
public class CategoryTest {
    @Resource
    private CategoryMapper categoryMapper;

    @Test
    void getIdByName(){
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .like(Category::getCategoryName, "测试分类"));
        System.out.println(category.getId());
    }
}
