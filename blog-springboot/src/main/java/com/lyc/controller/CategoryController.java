package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.config.annotation.VisitLogger;
import com.lyc.common.PageResult;
import com.lyc.common.Result;
import com.lyc.model.dto.CategoryDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.vo.ArticleConditionList;
import com.lyc.model.vo.CategoryBackVO;
import com.lyc.model.vo.CategoryVO;
import com.lyc.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/5/2 11:26
 */

@Api(value = "分类控制器",tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取分类选项")
    @GetMapping("/admin/category/option")
    public Result<List<CategoryVO>> getCategoryOption(){
        List<CategoryVO> categoryVOS= categoryService.getCategoryVo();
        return Result.success(categoryVOS);
    }


    @ApiOperation(value = "查看后台分类列表")
    @SaCheckPermission("blog:category:list")
    @GetMapping("/admin/category/list")
    public Result<PageResult<CategoryBackVO>> listCategoryBackVO(ConditionDTO condition) {
        return Result.success(categoryService.listCategoryBackVO(condition));
    }


    @ApiOperation(value = "添加分类")
    @SaCheckPermission("blog:category:add")
    @PostMapping("/admin/category/add")
    public Result<?> addCategory(@Validated @RequestBody CategoryDTO category) {
        categoryService.addCategory(category);
        return Result.success(null);
    }


    @ApiOperation(value = "删除分类")
    @SaCheckPermission("blog:category:delete")
    @DeleteMapping("/admin/category/delete")
    public Result<?> deleteCategory(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return Result.success(null);
    }


    @ApiOperation(value = "修改分类")
    @SaCheckPermission("blog:category:update")
    @PutMapping("/admin/category/update")
    public Result<?> updateCategory(@Validated @RequestBody CategoryDTO category) {
        categoryService.updateCategory(category);
        return Result.success(null);
    }


    @VisitLogger(value = "文章分类")
    @ApiOperation(value = "查看分类列表")
    @GetMapping("/category/list")
    public Result<List<CategoryVO>> listCategoryVO() {
        return Result.success(categoryService.listCategoryVO());
    }


    @VisitLogger(value = "分类文章")
    @ApiOperation(value = "查看分类下的文章")
    @GetMapping("/category/article")
    public Result<ArticleConditionList> listArticleCategory(ConditionDTO condition) {
        return Result.success(categoryService.listArticleCategory(condition));
    }
}
