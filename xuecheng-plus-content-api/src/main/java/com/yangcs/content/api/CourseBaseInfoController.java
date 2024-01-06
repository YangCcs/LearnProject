package com.yangcs.content.api;

import com.yangcs.content.model.PageParams;
import com.yangcs.content.model.PageResult;
import com.yangcs.content.model.dto.QueryCourseParamsDto;
import com.yangcs.content.model.po.CourseBase;
import com.yangcs.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController // 相当于Controller和ResponseBody的注解结合
public class CourseBaseInfoController {
    // QueryCourseParamsDto要从Json转化为Java对象，注解@RequestBody用来接受前端的Json字符串，Post请求
    // 在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，而@RequestParam()可以有多个。

    @Autowired
    CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list") // @RequestMapping("/course/list") 可以进行get/post/put/delete操作，结合体
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto) {
        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);
    }
}
