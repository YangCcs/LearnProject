package com.yangcs.content.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangcs.content.mapper.TeachplanMapper;
import com.yangcs.content.model.dto.SaveTeachplanDto;
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

    private int getTeachplanCount(Long courseId, Long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(Teachplan::getCourseId, courseId).eq(Teachplan::getParentid, parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count + 1;
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        // 通过课程计划的id来判断是新增还是修改
        Long teachplanId = saveTeachplanDto.getId();
        if (teachplanId == null) {
            // 新增
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);

            // 确定排序字段，找到它的同级节点个数，排序字段就是个数+1 select count(*) from teachplan where course_id=117 and parentid=268
            Long courseId = saveTeachplanDto.getCourseId();
            Long parentid = saveTeachplanDto.getParentid();
            Integer count = getTeachplanCount(courseId, parentid);
            teachplan.setOrderby(count);

            teachplanMapper.insert(teachplan);
        } else {
            // 修改
            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            // 将参数复制到teachplan
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        }
    }
}
