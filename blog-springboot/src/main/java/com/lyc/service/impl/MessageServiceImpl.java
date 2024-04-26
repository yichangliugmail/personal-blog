package com.lyc.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.common.PageResult;
import com.lyc.constant.ParamConstant;
import com.lyc.handler.ServiceException;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.MessageDTO;
import com.lyc.model.po.Message;
import com.lyc.model.vo.MessageVO;
import com.lyc.service.MessageService;
import com.lyc.mapper.MessageMapper;
import com.lyc.service.SiteConfigService;
import com.lyc.utils.IpUtils;
import com.lyc.utils.PageUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static com.lyc.enums.ZoneEnum.SHANGHAI;

/**
* @author 蜡笔
* @description 针对表【t_message】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService{

    @Resource
    private MessageMapper messageMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SiteConfigService siteConfigService;

    @Override
    public List<MessageVO> listMessageVO() {
        return messageMapper.selectMessageVoList();
    }

    @Override
    public PageResult<Message> listMessage(ConditionDTO condition) {
        PageResult<Message> pageResult = new PageResult<>();
        //查询数据量
        Long count = messageMapper.selectCount(new LambdaQueryWrapper<Message>()
                .eq(Objects.nonNull(condition.getIsCheck()),Message::getIsCheck,condition.getIsCheck())
                .or()
                .like(StringUtils.hasText(condition.getKeyword()),Message::getNickname,condition.getKeyword()));

        //数据量为0
        if(count==0L){
            return pageResult;
        }
        //数据量不为0
        pageResult.setCount(count);
        List<Message> messageList=messageMapper.selectBackMessage(PageUtils.getLimit(),PageUtils.getSize(),condition);

        pageResult.setRecordList(messageList);

        return pageResult;
    }

    @Override
    public void addMessage(MessageDTO messageDTO) {
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);

        //判断用户是否登录
        String token = StpUtil.getTokenValue();
        if(token==null){
            //游客头像，昵称
            String avatar = siteConfigService.getSiteConfig().getTouristAvatar();
            messageDTO.setAvatar(avatar);
            String nickName="游客"+ LocalDateTime.now();
            messageDTO.setNickname(nickName.substring(0,12));

        }

        Message message = Message.builder()
                .avatar(messageDTO.getAvatar())
                .nickname(messageDTO.getNickname())
                .messageContent(messageDTO.getMessageContent())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .isCheck(ParamConstant.CHECK_DEFAULT)
                .build();

        messageMapper.insert(message);
    }

    @Override
    public void deleteMessage(List<Integer> messageList) {
        int count = messageMapper.deleteBatchIds(messageList);
        if(count==0){
            throw new ServiceException("删除失败");
        }
    }


}




