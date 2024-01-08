package com.yangcs.content;

import com.yangcs.content.model.dto.CourseCategoryTreeDto;
import com.yangcs.content.service.CourseCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseCategoryServiceTest {

    @Autowired
    CourseCategoryService courseCategoryService;

    @Test
    public void courseCategoryServiceTest() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
