package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.ExceptionLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_exception_log】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.ExceptionLog
*/
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

    List<ExceptionLog> selectLogList(@Param("limit") Long limit,@Param("size") Long size,@Param("condition") ConditionDTO condition);
}




