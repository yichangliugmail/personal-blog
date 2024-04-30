package com.lyc.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 点赞类型枚举类
 *
 * @author 刘怡畅
 */

@Getter
@AllArgsConstructor
public enum LikeTypeEnum {

    //文章点赞策略
    ARTICLE("文章","ArticleLikeStrategy"),

    //评论点赞策略
    COMMENT("评论","CommentLikeStrategy");

    //点赞类型
    private final String type;

    //点赞策略
    private final String strategy;

}
