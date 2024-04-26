package com.lyc.service;

import com.lyc.model.po.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.TagOptionVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_tag】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface TagService extends IService<Tag> {

    /**
     * 获取标签选项，只要id和标签名
     * @return rr
     */
    List<TagOptionVO> getTagOptionVoList();
}
