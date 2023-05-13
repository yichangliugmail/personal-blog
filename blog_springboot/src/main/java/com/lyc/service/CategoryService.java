package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.CategoryDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.ArticleConditionList;
import com.lyc.model.vo.CategoryBackVO;
import com.lyc.model.vo.CategoryVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_category】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface CategoryService extends IService<Category> {

    List<CategoryVO> getCategoryVo();

    PageResult<CategoryBackVO> listCategoryBackVO(ConditionDTO condition);

    void addCategory(CategoryDTO category);

    void deleteCategory(List<Integer> categoryIdList);

    void updateCategory(CategoryDTO category);

    List<CategoryVO> listCategoryVO();

    ArticleConditionList listArticleCategory(ConditionDTO condition);
}
