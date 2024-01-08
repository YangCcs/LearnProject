package com.yangcs.content.service;

import com.yangcs.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

public interface CourseCategoryService {
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
