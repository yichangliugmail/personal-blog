package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/5/9 15:41
 */

@SpringBootTest
public class MessageMapperTest {
    @Resource
    private MessageMapper messageMapper;

    @Test
    void getListMessage(){
        ConditionDTO condition = new ConditionDTO();

        List<Message> messageList = messageMapper.selectBackMessage(1L, 1L, condition);
        System.out.println(messageList);
    }

}
