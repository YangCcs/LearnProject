package com.yangcs.content.service;

import com.yangcs.content.model.PageParams;
import com.yangcs.content.model.PageResult;
import com.yangcs.content.model.dto.AddCourseDto;
import com.yangcs.content.model.dto.CourseBaseInfoDto;
import com.yangcs.content.model.dto.QueryCourseParamsDto;
import com.yangcs.content.model.po.CourseBase;

/*
* 课程信息管理接口
* */
public interface CourseBaseInfoService {

    // 课程分页查询
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    // 新增课程，companyId：机构id，addCourseDto：课程信息，return：课程添加成功的详细信息
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    CourseBaseInfoDto getCourseBaseInfo(Long courseId);
}
