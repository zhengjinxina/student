package com.example.student.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("student")
@Data
public class Student {
    @TableId
    private String id;
    private String name;
    private Integer sex;
    private String createTime;
    private String updateTime;
    private String level;
}
