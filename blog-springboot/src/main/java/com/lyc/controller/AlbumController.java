package com.lyc.controller;

import com.lyc.model.common.PageResult;
import com.lyc.model.common.Result;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.vo.AlbumBackVO;
import com.lyc.service.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘怡畅
 * @description 相册控制器
 * @date 2023/5/25 15:53
 */

@Api(tags = "相册管理",value = "相册控制器")
@RestController
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @ApiOperation("相册列表")
    @GetMapping("/admin/album/list")
    public Result<PageResult<AlbumBackVO>> getAlbumBackList(ConditionDTO conditionDTO){
        PageResult<AlbumBackVO> pageResult=albumService.getAlbumBackList(conditionDTO);
        return Result.success(pageResult);
    }
}
