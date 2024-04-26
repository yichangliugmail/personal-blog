package com.lyc.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章贡献统计
 *
 * @author 刘怡畅
 */
@Data
@ApiModel(description = "文章贡献统计")
public class ArticleStatisticsVO {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String date;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer count;
}
