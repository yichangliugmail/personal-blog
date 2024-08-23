package com.lyc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 评论DTO
 *
 * @author liuYichang
 */
@Data
@ApiModel("评论DTO")
public class CommentDTO {
    /**
     * 类型id
     */
    @ApiModelProperty("评论类型id")
    private Integer typeId;

    /**
     * 类型 (1文章 2友链 3说说)
     */
    @ApiModelProperty("评论类型名(1文章 2友链 3说说)")
    private Integer commentType;

    /**
     * 父评论id
     */
    @ApiModelProperty("父评论id")
    private Integer parentId;

    /**
     * 回复评论id
     */
    @ApiModelProperty("回复评论id")
    private Integer replyId;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String commentContent;

    /**
     * 被回复用户id
     */
    @ApiModelProperty("被回复用户id")
    private Integer toUid;

}
