package com.yangcs.content.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class EditCourseDto extends AddCourseDto{
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
