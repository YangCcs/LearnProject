package com.yangcs.content;

import com.yangcs.content.mapper.CourseCategoryMapper;
import com.yangcs.content.model.dto.CourseCategoryTreeDto;
import com.yangcs.content.model.po.CourseCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseCategoryMapperTest {
    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Test
    public void testCourseCategoryMapper() {

        /*List<CourseCategoryTreeDto> result = courseCategoryMapper.selectTest();
        System.out.println(result);

        List<CourseCategoryTreeDto> courseCategories = courseCategoryMapper.selectTreeNodes("1");
        System.out.println(courseCategories);*/

        List<CourseCategoryTreeDto> courseCategoryTreeDtos1 = courseCategoryMapper.selectTreeNodes1();
        System.out.println(courseCategoryTreeDtos1);

        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes2("1");
        System.out.println(courseCategoryTreeDtos);

        List<CourseCategoryTreeDto> courseCategoryTreeDtos2 = courseCategoryMapper.selectTreeNode3();
        System.out.println(courseCategoryTreeDtos2);
    }
}
