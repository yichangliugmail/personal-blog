package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.AlbumBackVO;

/**
* @author 蜡笔
* @description 针对表【t_album】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface AlbumService extends IService<Album> {

    /**
     * 分页查询后台图片文件集合
     * @param conditionDTO 条件
     */
    PageResult<AlbumBackVO> getAlbumBackList(ConditionDTO conditionDTO);
}
