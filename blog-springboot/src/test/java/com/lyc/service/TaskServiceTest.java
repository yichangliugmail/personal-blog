package com.lyc.service;

import com.lyc.model.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.vo.TaskBackVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author LiuYiChang
 * @date 2023/6/11 16:53
 */
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testTaskBackVoList(){
        PageResult<TaskBackVO> taskBackVOList = taskService.getTaskBackVOList(new ConditionDTO());
        List<TaskBackVO> recordList = taskBackVOList.getRecordList();
        System.out.println(recordList);
    }
}
