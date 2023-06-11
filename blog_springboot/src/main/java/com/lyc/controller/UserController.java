package com.lyc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.lyc.annotation.AccessLimit;
import com.lyc.common.Result;
import com.lyc.model.dto.LoginDTO;
import com.lyc.model.dto.RegisterDTO;
import com.lyc.model.dto.UserInfoDTO;
import com.lyc.model.vo.*;
import com.lyc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lyc.enums.StatusCodeEnum.SUCCESS;

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
     * 后台登录
     * @return 携带Token返回
     */
    @ApiOperation(value = "后台登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDTO loginDto){
        String token = userService.login(loginDto);
        return Result.success(token);
    }

    /**
     * 退出登录
     * SaCheckLogin 鉴权，只有登录用户才能操作,但是token失效后，就会找不到token报错
     * @return rr
     */
    //@SaCheckLogin
    @ApiOperation(value = "用户退出")
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success(null,SUCCESS.getCode(),SUCCESS.getMsg());
    }

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

    @AccessLimit(seconds = 60,maxCount = 1)
    @ApiOperation("向邮箱发送验证码")
    @GetMapping("/code")
    public Result<?> sendCode(String username){
        userService.sendCode(username);
        return Result.success(null);
    }

    @ApiOperation(value = "用户邮箱注册")
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody RegisterDTO register) {
        userService.register(register);
        return Result.success(null);
    }

    @ApiOperation("修改前台用户信息")
    @SaCheckLogin
    @PutMapping("/user/info")
    public Result<?> saveUserInfo(UserInfoDTO userInfoDTO){
        userService.saveUserInfo(userInfoDTO);
        return Result.success(null);
    }



}
