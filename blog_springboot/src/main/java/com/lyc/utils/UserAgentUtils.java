package com.lyc.utils;


import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * 浏览器和操作系统解析工具类
 *
 * @author 刘怡畅
 */

public class UserAgentUtils {

    /**
     * 代理解析器
     */
    private static final UserAgentAnalyzer USER_AGENT_ANALYZER;

    static {
        USER_AGENT_ANALYZER = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withField(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR)
                .withField(UserAgent.AGENT_NAME_VERSION)
                .build();
    }

    /**
     * 从User-Agent解析客户端操作系统和浏览器版本
     *
     * @param userAgentHeader 从request.Header中获取到的
     */
    public static Map<String, String> parseOsAndBrowser(String userAgentHeader) {
        //解析成userAgent
        UserAgent agent = USER_AGENT_ANALYZER.parse(userAgentHeader);
        //获取操作系统信息
        String os = agent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION_MAJOR);
        //获取浏览器信息
        String browser = agent.getValue(UserAgent.AGENT_NAME_VERSION);
        Map<String, String> map = new HashMap<>(2);
        map.put("os", os);
        map.put("browser", browser);
        return map;
    }

}