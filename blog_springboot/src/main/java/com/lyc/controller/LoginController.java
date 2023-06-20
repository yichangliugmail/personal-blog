package com.lyc.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lyc.annotation.AccessLimit;
import com.lyc.common.Result;
import com.lyc.model.dto.CodeDTO;
import com.lyc.model.dto.LoginDTO;
import com.lyc.model.dto.RegisterDTO;
import com.lyc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.lyc.enums.StatusCodeEnum.SUCCESS;

/**
 * @author LiuYiChang
 * @date 2023/6/20 17:57
 */

@Api(tags = "登录注册模块")
@RestController
public class LoginController {

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

    @ApiOperation("gitee登录")
    @PostMapping("/oauth/gitee")
    public Result<String> giteeLogin(@RequestBody CodeDTO codeDTO){

        String msg=userService.giteeLogin(codeDTO);
        return Result.success(null);
    }
}
