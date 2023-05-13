package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.Talk;
import com.lyc.service.TalkService;
import com.lyc.mapper.TalkMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_talk】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk>
    implements TalkService{

}




