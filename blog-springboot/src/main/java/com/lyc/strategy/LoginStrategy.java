package com.lyc.strategy;

import com.lyc.model.dto.CodeDTO;

/**
 * 第三方登录策略
 *
 * @author LiuYiChang
 * @date 2023/6/20 19:51
 */
public interface LoginStrategy {

    /**
     * 登录接口
     * @param codeDTO
     * @return token
     */
    String login(CodeDTO codeDTO);
}
