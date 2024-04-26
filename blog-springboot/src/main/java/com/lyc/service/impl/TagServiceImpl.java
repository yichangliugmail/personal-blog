package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.po.Tag;
import com.lyc.model.vo.TagOptionVO;
import com.lyc.service.TagService;
import com.lyc.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService{

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagOptionVO> getTagOptionVoList() {

        return tagMapper.selectTagOptionList();
    }
}




