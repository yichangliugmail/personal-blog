package com.lyc.service;

import com.alibaba.fastjson2.JSONObject;
import com.lyc.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author liuYichang
 * @Date 2024/4/30 17:24
 **/

@Service
public class XxlJobService {
    public Map<String, Object> pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {
        String response = null;

        try{
           response = HttpClientUtil.doPost("http://localhost:8081/xxl-job-admin/jobinfo/pageList", null,new HashMap<>());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONObject.parseObject(response, Map.class);
    }
}
