package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.CommentVO;
import com.lyc.model.vo.ReplyCountVO;
import com.lyc.model.vo.ReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 分页查询父评论
     *
     */
    List<CommentVO> selectParentComment(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    /**
     * 查询每条父评论下的前三条子评论
     *
     */
    List<ReplyVO> selectReplyByParentIdList(@Param("parentCommentIdList") List<Integer> parentCommentIdList);

    /**
     * 根据父评论id查询回复数量
     *
     */
    List<ReplyCountVO> selectReplyCountByParentId(@Param("parentCommentIdList") List<Integer> parentCommentIdList);

    /**
     * 根据父评论id获取子评论集合
     */
    List<ReplyVO> selectReplyByParentId(@Param("limit") Long limit,@Param("size") Long size,@Param("commentId") String commentId);
}





