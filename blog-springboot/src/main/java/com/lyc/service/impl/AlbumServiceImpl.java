package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Album;
import com.lyc.model.vo.AlbumBackVO;
import com.lyc.service.AlbumService;
import com.lyc.mapper.AlbumMapper;
import com.lyc.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_album】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService{

    @Resource
    private AlbumMapper albumMapper;

    @Override
    public PageResult<AlbumBackVO> getAlbumBackList(ConditionDTO condition) {
        //查询文件数量,可能存在搜索条件，文件名
        Long count=albumMapper.selectCount(new LambdaQueryWrapper<Album>()
                .like(StringUtils.hasText(condition.getKeyword()),Album::getAlbumName,condition.getKeyword()));

        //文件数量为0
        if(count==0){
            return new PageResult<>();
        }

        //查询相册信息
        List<AlbumBackVO> albumBackVOList=albumMapper.selectAlbumBackVOList(PageUtils.getLimit(),PageUtils.getSize(),condition);

        return new PageResult<>(albumBackVOList,count);
    }
}




