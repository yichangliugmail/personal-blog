package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.TaskBackVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_task】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Task
*/
public interface TaskMapper extends BaseMapper<Task> {

    List<TaskBackVO> selectTaskBackVOList(@Param("limit") Long limit,@Param("size") Long size,@Param("condition") ConditionDTO condition);
}




