package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.OperationLog;
import com.lyc.service.OperationLogService;
import com.lyc.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_operation_log】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService{

}




