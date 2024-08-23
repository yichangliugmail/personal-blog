package com.lyc.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuYichang
 * @description 分页返回结果类
 * @date 2023/4/24 20:46
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页返回类")
public class PageResult<T> {

    @ApiModelProperty(value = "分页结果")
    private List<T> recordList;

    @ApiModelProperty(value = "分页总数", dataType = "long")
    private Long count;

}