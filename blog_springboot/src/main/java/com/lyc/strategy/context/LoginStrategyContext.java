package com.lyc.strategy.context;

import com.lyc.enums.LoginTypeEnum;
import com.lyc.model.dto.CodeDTO;
import com.lyc.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 登录策略上下文
 *
 * @author LiuYiChang
 * @date 2023/6/20 19:54
 */
public class LoginStrategyContext {

    @Autowired
    private Map<String, LoginStrategy> loginStrategyMap;

    public String executeLoginStrategy(CodeDTO codeDTO, LoginTypeEnum loginTypeEnum){
        LoginStrategy loginStrategy = loginStrategyMap.get(loginTypeEnum.getStrategy());
        return loginStrategy.login(codeDTO);
    }
}
