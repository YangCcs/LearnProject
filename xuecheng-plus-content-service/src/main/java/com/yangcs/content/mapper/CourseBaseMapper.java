package com.yangcs.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangcs.content.model.po.CourseBase;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * <p>
 * 课程基本信息 Mapper 接口
 * </p>
 *
 * @author yangcs
 * @since 2024-01-02
 */
public interface CourseBaseMapper extends BaseMapper<CourseBase> {

    @Select("select * from course_base where id=#{id}")
    CourseBase selectById(Long id);
}
