package com.lyc.mapper;

import com.lyc.model.po.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.TagOptionVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_tag】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    List<TagOptionVO> selectTagOptionList();
}




