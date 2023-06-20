package com.lyc.service;

import com.lyc.model.dto.CodeDTO;
import com.lyc.model.dto.LoginDTO;
import com.lyc.model.dto.RegisterDTO;
import com.lyc.model.dto.UserInfoDTO;
import com.lyc.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.*;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_user】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginDto 用户名密码
     * @return 登录令牌token
     */
    String login(LoginDTO loginDto);

    UserInfoVO getUserInfo();

    List<RouterVO> getUserMenu();

    List<UserRoleVO> listUserRoleDTO();

    UserBackInfoVO getUserBackInfo();

    /**
     * 向用户邮箱发送验证码
     * @param username 邮箱账号
     */
    void sendCode(String username);

    /**
     * 邮箱注册账号
     * @param register 注册信息
     */
    void register(RegisterDTO register);

    /**
     * 保存前台用户信息
     * @param userInfoDTO 用户信息
     */
    void saveUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 实现 gitee 登录
     * @param codeDTO 授权参数
     */
    String giteeLogin(CodeDTO codeDTO);
}
