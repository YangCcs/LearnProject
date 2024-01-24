package com.yangcs.content.service;

import com.yangcs.content.model.dto.SaveTeachplanDto;
import com.yangcs.content.model.dto.TeachplanDto;

import java.util.List;

public interface TeachplanService {

    // 根据课程id查询课程计划
    List<TeachplanDto> findTeachplanTree(Long courseId);

    // 新增/修改/保存课程计划
    void saveTeachplan(SaveTeachplanDto saveTeachplanDto);
}
