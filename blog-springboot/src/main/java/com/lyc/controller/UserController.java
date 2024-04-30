package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.lyc.common.Result;
import com.lyc.model.dto.UserInfoDTO;
import com.lyc.model.vo.*;
import com.lyc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lyc.constant.enums.StatusCodeEnum.SUCCESS;

/**
 * @author 刘怡畅
 * @description 用户操作控制器
 * @date 2023/4/24 20:44
 */

@Api(tags = "用户操作模块")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取前台登录用户信息
     *
     * @return {@link UserInfoVO} 用户信息
     */
    @SaCheckLogin
    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/user/getUserInfo")
    public Result<UserInfoVO> getUserInfo() {
        return Result.success(userService.getUserInfo(),SUCCESS.getCode(),SUCCESS.getMsg());
    }

    /**
     * 获取后台登录用户信息
     *
     * @return {@link UserInfoVO} 用户信息
     */
    @SaCheckLogin
    @ApiOperation(value = "获取后台登录用户信息")
    @GetMapping("/admin/user/getUserInfo")
    public Result<UserBackInfoVO> getUserBackInfo() {
        return Result.success(userService.getUserBackInfo(),SUCCESS.getCode(),"获取登录用户信息");
    }

    @ApiOperation(value = "获取目录菜单")
    @GetMapping("/admin/user/getUserMenu")
    public Result<List<RouterVO>> getUserMenu(){
        return Result.success(userService.getUserMenu(),SUCCESS.getCode(),"获取用户目录成功");
    }

    @ApiOperation(value = "查看用户角色选项")
    @SaCheckPermission("system:user:list")
    @GetMapping("/admin/user/role")
    public Result<List<UserRoleVO>> listUserRoleDTO() {
        return Result.success(userService.listUserRoleDTO(),SUCCESS.getCode(),"查看用户角色选项");
    }



    @ApiOperation("修改前台用户信息")
    @SaCheckLogin
    @PutMapping("/user/info")
    public Result<?> saveUserInfo(UserInfoDTO userInfoDTO){
        userService.saveUserInfo(userInfoDTO);
        return Result.success(null);
    }


}
