package com.yangcs.content.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/*
* 分页查询的参数
* */
@Data
@ToString
public class PageParams {
    // 当前页码
    @ApiModelProperty("当前页码")
    private Long pageNo = 1L;
    // 每页记录数默认值
    @ApiModelProperty("每页记录数")
    private Long pageSize = 20L;

    public PageParams() {}

    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
