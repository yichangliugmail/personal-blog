package com.lyc.mapper;

import com.lyc.model.po.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.CategoryBackVO;
import com.lyc.model.vo.CategoryOptionVO;
import com.lyc.model.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_category】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryVO> selectCategoryVO();

    List<CategoryVO> getCategoryVOList();

    List<CategoryBackVO> selectCategoryBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("keyword") String keyword);

}




