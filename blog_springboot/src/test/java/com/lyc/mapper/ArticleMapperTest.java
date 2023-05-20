package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 刘怡畅
 * @description mapper层测试
 * @date 2023/4/27 17:50
 */

@SpringBootTest
public class ArticleMapperTest {
    @Resource
    private ArticleMapper articleMapper;

    @Test
    void testCount(){
        ConditionDTO condition = new ConditionDTO();
        condition.setIsDelete(0);
        Long aLong = articleMapper.ArticleCount(condition);
        //Long aLong = articleMapper.selectCount(null);
        System.out.println("条件查询文章数量为："+aLong);
    }

}
