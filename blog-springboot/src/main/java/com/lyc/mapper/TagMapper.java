package com.lyc.mapper;

import com.lyc.model.po.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.TagOptionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_tag】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    List<TagOptionVO> selectTagOptionList();

    /**
     * 根据标签名查询标签集合
     * @param tagNameList 标签名集合
     * @return 标签实体类集合
     */
    List<Tag> selectTagList(@Param("tagNameList") List<String> tagNameList);

    /**
     * 批量保存新标签
     * @param newTagList 新标签
     */
    void saveBatchTag(@Param("newTagList") List<Tag> newTagList);
}




