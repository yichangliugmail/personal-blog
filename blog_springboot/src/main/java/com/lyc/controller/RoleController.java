package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.common.Result;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.common.PageResult;
import com.lyc.model.vo.RoleVO;
import com.lyc.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lyc.enums.StatusCodeEnum.SUCCESS;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/4/27 14:18
 */

@Api(value = "角色控制器", tags = "用户角色模块")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查看角色列表
     *
     * @param condition 查询条件
     * @return {@link RoleVO} 角色列表
     */
    @ApiOperation(value = "查看角色列表")
    @SaCheckPermission("system:role:list")
    @GetMapping("/admin/role/list")
    public Result<PageResult<RoleVO>> listRoleVO(ConditionDTO condition) {
        return Result.success(roleService.listRoleVO(condition),SUCCESS.getCode(),"");
    }
}
