package com.yangcs.content.model.dto;

import com.yangcs.content.model.po.Teachplan;
import com.yangcs.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TeachplanDto extends Teachplan {
    // 与媒资管理的信息
    private TeachplanMedia teachplanMedia;
    // 小章节list
    private List<TeachplanDto> teachPlanTreeNodes;
}
