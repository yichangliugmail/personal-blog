package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.MessageDTO;
import com.lyc.model.po.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.MessageBackVO;
import com.lyc.model.vo.MessageVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_message】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface MessageService extends IService<Message> {

    /**
     * 获取留言板留言集合
     *
     */
    List<MessageVO> listMessageVO();

    /**
     * 获取后台留言列表
     *
     */
    PageResult<Message> listMessage(ConditionDTO conditionDTO);

    /**
     * 发送留言
     */
    void addMessage(MessageDTO messageDTO);


}
