package com.lyc.strategy;

/**
 * @author 刘怡畅
 * @description 点赞策略
 * @date 2023/5/23 17:42
 */
public interface LikeStrategy {
    /**
     * 点赞方法
     * @param id 主键id
     */
    void like(Integer id);

}
