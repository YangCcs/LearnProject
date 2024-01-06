package com.yangcs.content;

import com.yangcs.content.model.PageParams;
import com.yangcs.content.model.PageResult;
import com.yangcs.content.model.dto.QueryCourseParamsDto;
import com.yangcs.content.model.po.CourseBase;
import com.yangcs.content.service.CourseBaseInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class) // 加了这个下面的courseBaseMapper就不报空指针错误了！！！！！！！！
public class CourseBaseInfoServiceTests {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;
    @Test
    public void testCourseBaseInfoService() {

        // 查询条件
        QueryCourseParamsDto courseParamsDto = new QueryCourseParamsDto();
        courseParamsDto.setCourseName("java"); // 课程名称查询条件
        courseParamsDto.setAuditStatus("202004"); // 课程审核通过的
        // 分页参数对象
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(2L);
        pageParams.setPageSize(2L);

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, courseParamsDto);
        System.out.println(courseBasePageResult);
    }
}
