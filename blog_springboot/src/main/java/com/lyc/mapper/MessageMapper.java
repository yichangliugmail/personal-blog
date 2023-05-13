package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.MessageBackVO;
import com.lyc.model.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_message】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Message
*/
public interface MessageMapper extends BaseMapper<Message> {

    List<MessageVO> selectMessageVoList();

    /**
     * 后台留言集合
     * @param limit 第几页
     * @param size 每页信息数量
     * @param condition 查询条件
     */
    List<Message> selectBackMessage(@Param("limit") Long limit, @Param("size") Long size,@Param("condition") ConditionDTO condition);
}




