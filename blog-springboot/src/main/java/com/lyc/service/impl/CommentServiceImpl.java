package com.lyc.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.common.PageResult;
import com.lyc.model.dto.CommentDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Comment;
import com.lyc.model.po.SiteConfig;
import com.lyc.model.vo.CommentBackVO;
import com.lyc.model.vo.CommentVO;
import com.lyc.model.vo.ReplyCountVO;
import com.lyc.model.vo.ReplyVO;
import com.lyc.service.CommentService;
import com.lyc.mapper.CommentMapper;
import com.lyc.service.RedisService;
import com.lyc.service.SiteConfigService;
import com.lyc.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lyc.constant.CommonConstant.FALSE;
import static com.lyc.constant.CommonConstant.TRUE;
import static com.lyc.constant.RedisConstant.COMMENT_LIKE_COUNT;

/**
* @author 蜡笔
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{


    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SiteConfigService siteConfigService;


    @Override
    public PageResult<CommentVO> listCommentVO(ConditionDTO condition) {
        // 查询父评论数量
        Long count = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(condition.getTypeId()), Comment::getTypeId, condition.getTypeId())
                .eq(Comment::getCommentType, condition.getCommentType())
                .eq(Comment::getIsCheck, TRUE).isNull(Comment::getParentId));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询父评论
        List<CommentVO> commentVOList = commentMapper.selectParentComment(PageUtils.getLimit(), PageUtils.getSize(), condition);
        if (CollectionUtils.isEmpty(commentVOList)) {
            return new PageResult<>();
        }
        // 评论点赞
        Map<String, Integer> likeCountMap = redisService.getHashAll(COMMENT_LIKE_COUNT);
        // 父评论id集合
        List<Integer> parentCommentIdList = commentVOList.stream().map(CommentVO::getId).collect(Collectors.toList());
        // 分组查询每组父评论下的子评论前三条
        List<ReplyVO> replyVOList = commentMapper.selectReplyByParentIdList(parentCommentIdList);
        // 封装子评论点赞量
        replyVOList.forEach(item -> item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0)));
        // 根据父评论id生成对应子评论的Map
        Map<Integer, List<ReplyVO>> replyMap = replyVOList.stream().collect(Collectors.groupingBy(ReplyVO::getParentId));
        // 父评论的回复数量
        List<ReplyCountVO> replyCountList = commentMapper.selectReplyCountByParentId(parentCommentIdList);
        // 转换Map
        Map<Integer, Integer> replyCountMap = replyCountList.stream().collect(Collectors.toMap(ReplyCountVO::getCommentId, ReplyCountVO::getReplyCount));
        // 封装评论数据
        commentVOList.forEach(item -> {
            item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            item.setReplyVOList(replyMap.get(item.getId()));
            item.setReplyCount(Optional.ofNullable(replyCountMap.get(item.getId())).orElse(0));
        });
        return new PageResult<>(commentVOList, count);
    }

    @Override
    public void addCommend(CommentDTO commentDTO) {
        //判断作者是否设置了评论审核
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        Integer check = siteConfig.getCommentCheck();

        //封装
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setFromUid(StpUtil.getLoginIdAsInt());
        comment.setIsCheck(check.equals(FALSE)?TRUE:FALSE);

        //保存
        commentMapper.insert(comment);

    }

    @Override
    public List<ReplyVO> replyList(String commentId) {
        //根据夫评论id查询子评论集合
        List<ReplyVO> replyVOList= commentMapper.selectReplyByParentId(PageUtils.getLimit(),PageUtils.getSize(),commentId);
        //获取点赞map集合
        Map<String, Object> likeCountMap = redisService.getHashAll(COMMENT_LIKE_COUNT);
        //从集合中取出属于该评论的点赞项
        for(ReplyVO item:replyVOList){
            item.setLikeCount((Integer) likeCountMap.getOrDefault(item.getId().toString(),0));
        }

        return replyVOList;
    }

    @Override
    public PageResult<CommentBackVO> listCommentBackVO(ConditionDTO condition) {
        PageResult<CommentBackVO> pageResult = new PageResult<>();
        //获取评论用户昵称
        Long count=commentMapper.commentCount(condition);
        pageResult.setCount(count);
        if(count==0){
            return pageResult;
        }

        List<CommentBackVO> commentBackVOList=commentMapper.selectCommentBackVOList(PageUtils.getLimit(),PageUtils.getSize(),condition);
        pageResult.setRecordList(commentBackVOList);
        return pageResult;
    }


}




