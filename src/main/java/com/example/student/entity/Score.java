package com.example.student.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("score")
@Data
public class Score {
    @TableId
    private String id;
    private String studentName;
    private String courseName;
    private String score;
    private String createTime;
    private String updateTime;
}
