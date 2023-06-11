package com.lyc.config.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyc.mapper.UserMapper;
import com.lyc.model.po.User;
import com.lyc.model.vo.OnlineVO;
import com.lyc.utils.IpUtils;
import com.lyc.utils.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import static com.lyc.constant.CommonConstant.ONLINE_USER;
import static com.lyc.enums.ZoneEnum.SHANGHAI;

/**
 * 自定义 SaToken 监听器的实现
 *
 * @author 刘怡畅
 */
@Component
public class MySaTokenListener implements SaTokenListener {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        // 查询用户昵称和头像
        User user =userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getAvatar,User::getNickname)
                .eq(User::getId,loginId));
        //解析浏览器和操作系统
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User_Agent"));
        //获取登录IP
        String ipAddress = IpUtils.getIpAddress(request);
        //解析IP为地址
        String ipSource = IpUtils.getIpSource(ipAddress);
        //获取登录时间
        LocalDateTime longinTime = LocalDateTime.now(ZoneId.of(SHANGHAI.getZone()));
        //封装在线用户对象
        OnlineVO onlineVO = OnlineVO.builder()
                .id((Integer) loginId)
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .os(userAgentMap.get("os"))
                .browser(userAgentMap.get("browser"))
                .loginTime(longinTime)
                .build();
        //更新登录用户信息
        User newUser = User.builder()
                .id((Integer) loginId)
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .loginTime(longinTime)
                .build();
        userMapper.updateById(newUser);

        //在线用户信息存入tokenSession
        SaSession saSession = StpUtil.getTokenSessionByToken(tokenValue);
        saSession.set(ONLINE_USER,onlineVO);
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        // 删除缓存中的用户信息
        StpUtil.logoutByTokenValue(tokenValue);
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
    }

    /**
     * 每次二级认证时触发
     */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
    }

    /**
     * 每次退出二级认证时触发
     */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
    }

    /**
     * 每次 Token 续期时触发
     */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
    }
}
