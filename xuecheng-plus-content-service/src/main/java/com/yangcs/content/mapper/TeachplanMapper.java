package com.yangcs.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangcs.content.model.dto.TeachplanDto;
import com.yangcs.content.model.po.Teachplan;
import com.yangcs.content.model.po.TeachplanMedia;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author yangcs
 * @since 2024-01-02
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    // 查询某课程的课程计划，组成树形结构
    // @Select("select * from teachplan where course_id=#{courseId}")
    List<TeachplanDto> selectTreeNodes(long courseId);

    List<TeachplanDto> selectTreeNodesOne(long courseId);

    @Select("select * from teachplan where parentid=0 and course_id=#{courseId}")
    List<Teachplan> selectTreeNodesParent(long courseId);

    @Select("select * from teachplan where parentid=#{parentid}")
    List<Teachplan> selectTreeNodesSon(long parentid);

    @Select("select * from teachplan_media where teachplan_id = #{id}")
    TeachplanMedia selectMedia(long id);

}
