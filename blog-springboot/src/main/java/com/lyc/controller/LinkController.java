package com.lyc.controller;

import com.lyc.model.common.Result;
import com.lyc.model.po.Friend;
import com.lyc.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 刘怡畅
 * @description 开源链接控制器
 * @date 2023/5/9 16:57
 */

@Api(value = "开源链接控制器", tags = "链接模块")
@RestController
public class LinkController {

    @Autowired
    private FriendService friendService;


    @ApiOperation("链接数据集合")
    @GetMapping("/friend/list")
    public Result<List<Friend>> getFriendList(){
        return Result.success(friendService.list());
    }
}
