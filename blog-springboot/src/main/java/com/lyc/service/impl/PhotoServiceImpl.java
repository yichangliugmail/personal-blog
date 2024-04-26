package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.Photo;
import com.lyc.service.PhotoService;
import com.lyc.mapper.PhotoMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_photo】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo>
    implements PhotoService{

}




