package com.lyc.strategy.context;

import com.lyc.enums.LikeTypeEnum;
import com.lyc.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 刘怡畅
 * @description 点赞策略上下文
 * @date 2023/5/23 17:45
 */

@Service
public class LikeStrategyContext {

    /**
     * key：策略字符串
     * value：对应的实现类
     */
    @Autowired
    private Map<String, LikeStrategy> likeStrategyMap;

    /**
     * 执行点赞策略
     * @param likeType 点赞类型（文章，评论）
     * @param id 主键id
     */
    public void executeLikeStrategy(LikeTypeEnum likeType,Integer id){
        LikeStrategy likeStrategy = likeStrategyMap.get(likeType.getStrategy());
        likeStrategy.like(id);
    }
}
