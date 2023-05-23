package com.lyc.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lyc.service.RedisService;
import com.lyc.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lyc.constant.RedisConstant.ARTICLE_LIKE_COUNT;
import static com.lyc.constant.RedisConstant.USER_ARTICLE_LIKE;

/**
 * @author 刘怡畅
 * @description 文章点赞策略实现类
 * @date 2023/5/23 17:59
 */

@Service("ArticleLikeStrategy")
public class ArticleLikeStrategy implements LikeStrategy {

    @Autowired
    private RedisService redisService;

    @Override
    public void like(Integer articleId) {
        String key=USER_ARTICLE_LIKE+ StpUtil.getLoginIdAsInt();
        boolean isLike=redisService.hasSetValue(key,articleId);
        //判断是否点赞了
        if(isLike){
            //没点赞，进行点赞
            //标记该用户已点赞
            redisService.setSet(key,articleId);
            //点赞总量加一
            redisService.incrHash(ARTICLE_LIKE_COUNT,articleId.toString(),1L);
        }else {
            //点赞了，取消点赞
            //标记该用户未点赞
            redisService.deleteSet(key,articleId);
            //点赞总量减一
            redisService.decrHash(key,articleId.toString(),1L);
        }

    }
}
