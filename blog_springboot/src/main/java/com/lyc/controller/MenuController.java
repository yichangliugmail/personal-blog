package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.common.PageResult;
import com.lyc.common.Result;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.vo.MenuVO;
import com.lyc.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/5/9 18:21
 */

@Api(tags = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("菜单集合")
    @SaCheckPermission("system:menu:list")
    @GetMapping("/admin/menu/list")
    public Result<List<MenuVO>> getListMenu(ConditionDTO conditionDTO){

        return Result.success(menuService.getListMenu(conditionDTO));
    }
}
