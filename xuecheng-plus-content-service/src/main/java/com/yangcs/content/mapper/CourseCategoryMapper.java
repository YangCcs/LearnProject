package com.yangcs.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangcs.content.model.dto.CourseCategoryTreeDto;
import com.yangcs.content.model.po.CourseCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author yangcs
 * @since 2024-01-02
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategoryTreeDto> selectTreeNodes2(String id);

    List<CourseCategoryTreeDto> selectTest();

    // @Select("select tb1.id, tb1.label, tb2.id, tb2.label from course_category tb1 inner join course_category tb2 on tb1.id = tb2.parentid")
    @Select("select tb1.id, tb1.label, tb2.id, tb2.label from course_category tb1, course_Category tb2 where tb1.id = tb2.parentid")
    List<CourseCategoryTreeDto> selectTreeNodes1();

    @Select("select * from course_category")
    List<CourseCategoryTreeDto> selectTreeNodes(String id);

    @Select("select tb1.id tb1_id, tb1.name tb1_name, tb1.parentid tb1_parent, tb1.orderby tb1_orderby, tb1.label tb1_label, " +
            "tb2.id tb2_id, tb2.name tb2_name, tb2.parentid tb2_parentid, tb2.orderby tb2_orderby, tb2.label tb2_label " +
            "from course_category tb1 inner join course_category tb2 on tb1.id = tb2.parentid " +
            "where tb1.parentid=1 and tb1.is_show=1 and tb2.is_show=1 order by tb1.orderby, tb2.orderby")
    List<CourseCategoryTreeDto> selectTreeNode3();


}
