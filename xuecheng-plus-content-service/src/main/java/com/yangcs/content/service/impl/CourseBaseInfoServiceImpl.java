package com.yangcs.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangcs.base.exception.XueChengPlusException;
import com.yangcs.content.mapper.CourseBaseMapper;
import com.yangcs.content.mapper.CourseCategoryMapper;
import com.yangcs.content.mapper.CourseMarketMapper;
import com.yangcs.content.model.PageParams;
import com.yangcs.content.model.PageResult;
import com.yangcs.content.model.dto.AddCourseDto;
import com.yangcs.content.model.dto.CourseBaseInfoDto;
import com.yangcs.content.model.dto.QueryCourseParamsDto;
import com.yangcs.content.model.po.CourseBase;
import com.yangcs.content.model.po.CourseMarket;
import com.yangcs.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {

        // 拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 根据名称模糊查询,在sql中拼接course_base.name like '%值%'
        queryWrapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()), CourseBase::getName, courseParamsDto.getCourseName());
        // 根据课程的审核状态查询 course_base.audit_status = ?
        queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getCourseName()), CourseBase::getAuditStatus, courseParamsDto.getAuditStatus());

        // 创建page分页参数对象，参数：当前页码，每页记录数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> items = pageResult.getRecords(); // 数据列表
        long total = pageResult.getTotal();
        // List<T> iterm, long counts, long page, log pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        System.out.println(courseBasePageResult);

        return courseBasePageResult;
    }

    @Transactional // 数据库写入操作都要进行事务控制
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        // 参数的合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            // throw new RuntimeException("课程名称为空");
            XueChengPlusException.cast("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }

        // 向两张表中写入数据：课程基本信息表course_base和课程营销表course_market
        CourseBase courseBase = new CourseBase();
        // 将传入的页面的参数放到courseBase对象中
        // courseBase.setName(dto.getName()); 这种方式，从原对象中拿数据向新对象set，比较复杂
        BeanUtils.copyProperties(dto, courseBase); // 拷贝时按照属性的名称进行拷贝！【完全拷贝】
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        // 审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        // 发布状态为未发布
        courseBase.setStatus("203001");
        // 插入数据库
        int insert = courseBaseMapper.insert(courseBase);
        if (insert <= 0) {
            throw new RuntimeException("添加课程失败");
        }
        // 向课程营销表course_market写入数据-课程的id---------------------------------
        CourseMarket courseMarket = new CourseMarket();
        // 将页面的数据拷贝到courseMarket
        BeanUtils.copyProperties(dto, courseMarket);
        courseMarket.setId(courseBase.getId());
        // 保存营销的信息
        saveCourseMarket(courseMarket);
        // 从数据库查询课程详细信息，包括两部分：基本信息和营销信息
        CourseBaseInfoDto courseBaseInfoDto = getCourseBaseInfo(courseBase.getId());

        return courseBaseInfoDto;
    }
    // 查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        // 从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        // 从课程营销表查询（同一个id主键）
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        // todo 通过courseCategoryMapper查询分类信息，将分类信息放在courseBaseInfoDto中取
        return courseBaseInfoDto;
    }

    // 单独写一个方法保存营销信息，逻辑：存在则更新，不存在则添加
    private int saveCourseMarket(CourseMarket courseMarket) {
        // 参数的各发校验
        String charge = courseMarket.getCharge();
        if (StringUtils.isEmpty(charge)) {
            throw new RuntimeException("收费规则为空");
        }
        // 如果课程收费，价格没有填写也需要抛出异常
        if (charge.equals("201001")) {
            if (courseMarket.getPrice() == null || courseMarket.getPrice() <= 0) {
                throw new RuntimeException("课程的价格不能为空并且必须大于0");
            }
        }
        // 从数据库查询营销信息，存在则更新，不存在则添加
        Long id = courseMarket.getId(); // 主键
        CourseMarket courseMarket1 = courseMarketMapper.selectById(id);
        if (courseMarket1 == null) {
            // 插入数据库
            int insert = courseMarketMapper.insert(courseMarket);
            return insert;
        } else {
            // 更新，将传入的数据更新到数据库中
            BeanUtils.copyProperties(courseMarket, courseMarket1);
            courseMarket1.setId(courseMarket.getId());
            // 更新
            int i = courseMarketMapper.updateById(courseMarket1);
            return i;
        }
    }
}
