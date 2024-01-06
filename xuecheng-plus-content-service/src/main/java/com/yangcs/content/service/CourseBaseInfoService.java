package com.yangcs.content.service;

import com.yangcs.content.model.PageParams;
import com.yangcs.content.model.PageResult;
import com.yangcs.content.model.dto.QueryCourseParamsDto;
import com.yangcs.content.model.po.CourseBase;

/*
* 课程信息管理接口
* */
public interface CourseBaseInfoService {

    // 课程分页查询
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);
}
