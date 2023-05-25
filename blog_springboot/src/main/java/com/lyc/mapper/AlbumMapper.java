package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.AlbumBackVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_album】的数据库操作Mapper
* @createDate 2023-04-24 21:06:35
* @Entity com.lyc.po.Album
*/
public interface AlbumMapper extends BaseMapper<Album> {

    /**
     * 条件查询相册数据
     * @param limit 数据起始位置
     * @param size 本页数据量
     * @param condition 条件
     */
    List<AlbumBackVO> selectAlbumBackVOList(@Param("limit") Long limit,@Param("size") Long size,@Param("condition") ConditionDTO condition);
}




