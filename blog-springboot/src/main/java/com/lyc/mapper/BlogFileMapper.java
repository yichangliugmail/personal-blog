package com.lyc.mapper;

import com.lyc.model.dto.FileQuery;
import com.lyc.model.dto.FileResp;
import com.lyc.model.po.BlogFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_blog_file】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.BlogFile
*/
public interface BlogFileMapper extends BaseMapper<BlogFile> {

    /**
     * 查询后台文件列表
     *
     * @param fileQuery 文件条件
     * @return 后台文件列表
     */
    List<FileResp> selectFileVOList(@Param("param") FileQuery fileQuery);
}




