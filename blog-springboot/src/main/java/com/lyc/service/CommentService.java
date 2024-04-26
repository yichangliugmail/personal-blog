package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.CommentDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.MessageDTO;
import com.lyc.model.po.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.CommentBackVO;
import com.lyc.model.vo.CommentVO;
import com.lyc.model.vo.ReplyVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface CommentService extends IService<Comment> {

    /**
     * 前台评论集合
     */
    PageResult<CommentVO> listCommentVO(ConditionDTO condition);

    /**
     * 发表评论
     */
    void addCommend(CommentDTO commentDTO);

    /**
     * 获取评论集合
     */
    List<ReplyVO> replyList(String commentId);

    /**
     * 分页查询后台评论集合
     */
    PageResult<CommentBackVO> listCommentBackVO(ConditionDTO conditionDTO);
}
