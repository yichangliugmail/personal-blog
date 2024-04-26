package com.lyc.service;

import com.lyc.model.vo.BlogBackInfoVO;
import com.lyc.model.vo.BlogInfoVO;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/4/25 17:52
 */
public interface BlogInfoService {
    /**
     * 更新访问量
     */
    void report();

    BlogBackInfoVO getBlogBackInfo();

    BlogInfoVO getBlogInfo();
}
