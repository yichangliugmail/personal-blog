package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.BlogFile;
import com.lyc.service.BlogFileService;
import com.lyc.mapper.BlogFileMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_blog_file】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class BlogFileServiceImpl extends ServiceImpl<BlogFileMapper, BlogFile>
    implements BlogFileService{

}




