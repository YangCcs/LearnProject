package com.yangcs.content.service.impl;


import com.yangcs.content.mapper.TeachplanMapper;
import com.yangcs.content.model.dto.TeachplanDto;
import com.yangcs.content.model.po.Teachplan;
import com.yangcs.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        // 查询到的父节点
        List<Teachplan> teachplans = teachplanMapper.selectTreeNodesParent(courseId);

        List<TeachplanDto> result = new ArrayList<>();
        for (Teachplan teachplan : teachplans) {
            long id = teachplan.getId(); // 父节点的id
            TeachplanDto tmp = new TeachplanDto();
            // 把父节点的数据复制到tmp中
            BeanUtils.copyProperties(teachplan, tmp);
            // 根据父节点id查询子节点
            List<Teachplan> treeNodes = teachplanMapper.selectTreeNodesSon(id);
            List<TeachplanDto> treeNodeNew = new ArrayList<>();
            for (Teachplan treeNode : treeNodes) {
                TeachplanDto teachplanDto = new TeachplanDto();
                BeanUtils.copyProperties(treeNode, teachplanDto);
                teachplanDto.setTeachplanMedia(teachplanMapper.selectMedia(treeNode.getId()));
                treeNodeNew.add(teachplanDto);
            }
            tmp.setTeachPlanTreeNodes(treeNodeNew);
            result.add(tmp);
        }

        return result;
    }
}
