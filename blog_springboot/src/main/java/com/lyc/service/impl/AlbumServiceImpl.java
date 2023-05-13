package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.Album;
import com.lyc.service.AlbumService;
import com.lyc.mapper.AlbumMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【t_album】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album>
    implements AlbumService{

}




