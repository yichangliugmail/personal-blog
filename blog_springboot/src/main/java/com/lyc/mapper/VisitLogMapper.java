package com.lyc.mapper;

import cn.hutool.core.date.DateTime;
import com.lyc.model.po.VisitLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.UserViewVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_visit_log】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.VisitLog
*/
public interface VisitLogMapper extends BaseMapper<VisitLog> {

    List<UserViewVO> selectUserViewList(DateTime startTime, DateTime endTime);
}




