package com.lyc.controller;

import com.lyc.common.Result;
import com.lyc.model.po.Tag;
import com.lyc.model.vo.TagOptionVO;
import com.lyc.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 刘怡畅
 * @description
 * @date 2023/5/2 11:17
 */

@Api(value = "标签控制器",tags = "标签")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("获取标签集合")
    @GetMapping("/admin/tag/option")
    public Result<List<TagOptionVO>> getTagOption(){
        List<TagOptionVO> tagOptionVOList=tagService.getTagOptionVoList();
        return Result.success(tagOptionVOList);
    }
}
