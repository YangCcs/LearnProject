package com.yangcs.content;


import com.yangcs.content.mapper.TeachplanMapper;
import com.yangcs.content.model.dto.TeachplanDto;
import com.yangcs.content.service.TeachplanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

// 课程计划mapper测试
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TeachplanMapperTest {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanService teachplanService;

    @Test
    public void testSelectTreeNodes() {
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(117L);
        System.out.println(teachplanTree);
    }
}
