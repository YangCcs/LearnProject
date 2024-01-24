package com.yangcs.content.api;

import com.yangcs.base.exception.ValidationGroups;
import com.yangcs.base.model.PageParams;
import com.yangcs.base.model.PageResult;
import com.yangcs.content.model.dto.AddCourseDto;
import com.yangcs.content.model.dto.CourseBaseInfoDto;
import com.yangcs.content.model.dto.EditCourseDto;
import com.yangcs.content.model.dto.QueryCourseParamsDto;
import com.yangcs.content.model.po.CourseBase;
import com.yangcs.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
* 课程基础信息相关接口
* */
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController // 相当于Controller和ResponseBody的注解结合
public class CourseBaseInfoController {
    // QueryCourseParamsDto要从Json转化为Java对象，注解@RequestBody用来接受前端的Json字符串，Post请求
    // 在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，而@RequestParam()可以有多个。

    @Autowired
    CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("课程分页查询接口")
    @PostMapping("/course/list") // @RequestMapping("/course/list") 可以进行get/post/put/delete操作，结合体
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto) {
        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);
    }

    @ApiOperation("新增课程")
    @PostMapping("/course")  // 得到的是JSON数据，转化为Java对象传入参数啊，所以用这个注解!!!!!!!!!!
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto) {
        // 获取用户 所属机构的id
        Long companyId = 1232141425L;
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }

    @ApiOperation("根据课程id查询接口")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated(ValidationGroups.Update.class) EditCourseDto editCourseDto) {
        // 获取用户 所属机构的id
        Long companyId = 1232141425L;
        CourseBaseInfoDto courseBaseInfoDto = courseBaseInfoService.updateCourseBase(companyId, editCourseDto);
        return courseBaseInfoDto;
    }
}
