package com.example.student.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.student.common.AwsS3Utils;
import com.example.student.common.DateTool;
import com.example.student.common.Result;
import com.example.student.entity.Score;
import com.example.student.entity.Student;
import com.example.student.mapper.StudentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    StudentMapper studentMapper;

    @PostMapping
    public Result<?> save(@RequestBody Student student){
        student.setId(UUID.randomUUID().toString());
        student.setCreateTime(new DateTool().FormatCurrentTime());
        studentMapper.insert(student);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Student::getName, search);
        }
        Page<Student> studentPage = studentMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(studentPage);
    }

    @PutMapping
    public Result<?> update(@RequestBody Student student){
        student.setUpdateTime(new DateTool().FormatCurrentTime());
        studentMapper.updateById(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id){
        studentMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/studentList")
    public Result<?> studentList(){
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        List<Student> scores = studentMapper.selectList(wrapper);
        return Result.success(scores);
    }
}
