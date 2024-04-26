package com.lyc.strategy.abst;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyc.mapper.UserMapper;
import com.lyc.mapper.UserRoleMapper;
import com.lyc.model.dto.CodeDTO;
import com.lyc.model.po.User;
import com.lyc.model.po.UserRole;
import com.lyc.model.vo.SocialTokenVO;
import com.lyc.model.vo.SocialUserInfoVO;
import com.lyc.strategy.LoginStrategy;

import javax.annotation.Resource;
import java.util.Objects;

import static com.lyc.enums.RoleEnum.USER;

/**
 * 抽象登录模板类
 *
 * @author LiuYiChang
 * @date 2023/6/20 20:03
 */
public abstract class AbstractLoginStrategy implements LoginStrategy {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public String login(CodeDTO codeDTO) {
        SocialTokenVO socialToken = getSocialToken(codeDTO);
        SocialUserInfoVO socialUserInfo = getSocialUserInfo(socialToken);

        //判断用户是否存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId)
                .eq(User::getUsername, socialUserInfo.getId())
                .eq(User::getLoginType, socialToken.getLoginType()));

        if(Objects.isNull(user)){
            //注册新账户
            user = saveUser(socialToken, socialUserInfo);
        }
        //是否封号
        StpUtil.checkDisable(user.getId());
        //登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    /**
     * 获取第三方token
     * @param codeDTO
     * @return token数据
     */
    public abstract SocialTokenVO getSocialToken(CodeDTO codeDTO);

    /**
     * 获取第三方用户信息
     * @param socialTokenVO
     * @return
     */
    public abstract SocialUserInfoVO getSocialUserInfo(SocialTokenVO socialTokenVO);

    private User saveUser(SocialTokenVO socialToken,SocialUserInfoVO socialUserInfo){
        User newUser = User.builder()
                .avatar(socialUserInfo.getAvatar())
                .nickname(socialUserInfo.getNickname())
                .username(socialUserInfo.getId())
                .password(socialToken.getAccessToken())
                .loginType(socialToken.getLoginType())
                .build();
        userMapper.insert(newUser);
        // 新增用户角色
        UserRole userRole = UserRole.builder()
                .userId(newUser.getId())
                .roleId(USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        return newUser;
    }
}
