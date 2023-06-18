package com.lyc.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiuYiChang
 * @date 2023/6/18 16:52
 */

@Data
@ApiModel(description = "修改定时任务状态")
public class TaskStatusVO {

    @ApiModelProperty("任务id")
    private Integer id;

    @ApiModelProperty("任务状态")
    private Integer status;
}
