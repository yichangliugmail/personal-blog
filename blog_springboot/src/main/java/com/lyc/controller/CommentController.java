package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.common.PageResult;
import com.lyc.common.Result;
import com.lyc.model.dto.CommentDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.MessageDTO;
import com.lyc.model.po.Comment;
import com.lyc.model.po.Message;
import com.lyc.model.vo.*;
import com.lyc.service.CommentService;
import com.lyc.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lyc.enums.StatusCodeEnum.SUCCESS;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/5/3 21:21
 */

@Api(value = "评论控制器",tags = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MessageService messageService;

    @ApiOperation("获取最新评论")
    @GetMapping("/recent/comment")
    public Result<List<RecentCommentVO>> getRecentComment(){
        return Result.success(null);
    }

    @ApiOperation(value = "查看评论")
    @GetMapping("/comment/list")
    public Result<PageResult<CommentVO>> listCommentVO(ConditionDTO condition) {
        return Result.success(commentService.listCommentVO(condition));
    }

    @ApiOperation("发表评论")
    @PostMapping("/comment/add")
    @SaCheckPermission("news:comment:add")
    public Result<?> addComment(@RequestBody CommentDTO commentDTO){
        commentService.addCommend(commentDTO);
        return Result.success(SUCCESS.getMsg());
    }

    @ApiOperation("查看回复评论")
    @PostMapping("/comment/{commentId}/reply")
    public Result<List<ReplyVO>> replyComment(@PathVariable("commentId") String commentId){
        List<ReplyVO> replyVOList=commentService.replyList(commentId);
        return Result.success(replyVOList);
    }

    @ApiOperation("后台评论集合")
    @GetMapping("/admin/comment/list")
    public Result<PageResult<CommentBackVO>> commentList(ConditionDTO conditionDTO){
        PageResult<CommentBackVO> pageResult=commentService.listCommentBackVO(conditionDTO);
        return Result.success(pageResult);
    }




    //=======留言板=======

    @ApiOperation("前台留言列表")
    @GetMapping("/message/list")
    public Result<List<MessageVO>> listMessageVO(){
        return Result.success(messageService.listMessageVO());
    }

    @ApiOperation("后台留言列表")
    @GetMapping("/admin/message/list")
    public Result<PageResult<Message>> listBackMessage(ConditionDTO conditionDTO){
        return Result.success(messageService.listMessage(conditionDTO));
    }

    @ApiOperation("发送留言")
    @PostMapping("/message/add")
    public Result<Object> addMessage(@RequestBody MessageDTO messageDTO){
        messageService.addMessage(messageDTO);
        return Result.success("发送成功");
    }



}
