package com.yangcs.content.service.impl;

import com.yangcs.content.mapper.CourseCategoryMapper;
import com.yangcs.content.model.dto.CourseCategoryTreeDto;
import com.yangcs.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        // 调用mapper递归查询出分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        // 找到每个结点的子节点，最终封装成List<CourseCategoryTreeDto>
        // 先将List转为map，key就是结点的id，value就是CourseCategoryTreeDto对象，目的是为了方便从map获取节点
        // .filter(item->!id.equals(item.getId())作用是为了排除根节点这一个结点
        Map<String, CourseCategoryTreeDto> map = courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId()))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key1));
        // 定义一个list作为最终返回的list
        List<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();
        // 从头遍历List<CourseCategoryTreeDto>，一遍遍历一边找子节点放在父节点的childrenTreeNodes
        courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).forEach(item->{
            // 开始处理，向list中写入元素
            if (item.getParentid().equals(id)) {
                courseCategoryList.add(item);
            }
            // 找到结点的父节点
            CourseCategoryTreeDto courseCategoryTreeDto = map.get(item.getParentid());
            if(courseCategoryTreeDto != null) {
                if (courseCategoryTreeDto.getChildrenTreeNodes() == null) {
                    // 如果该父节点的ChildrenTreeNodes属性为空，要new一个集合出来，因为要向集合中放它的子节点
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                // 将每个结点的子节点放在父节点的childrenTreeNodes属性中
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }
        });

        return courseCategoryList;
    }
}
