package com.example.student.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.student.common.DateTool;
import com.example.student.common.Result;
import com.example.student.entity.Score;
import com.example.student.mapper.ScoreMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    ScoreMapper scoreMapper;

    @PostMapping
    public Result<?> save(@RequestBody Score score){
        score.setId(UUID.randomUUID().toString());
        score.setCreateTime(new DateTool().FormatCurrentTime());
        scoreMapper.insert(score);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String studentName
                              ){
        LambdaQueryWrapper<Score> wrapper = Wrappers.<Score>lambdaQuery();
        if (StrUtil.isNotBlank(search) && StrUtil.isNotBlank(studentName)){
            wrapper.like(Score::getCourseName, search).like(Score::getStudentName, studentName);
        }else if (StrUtil.isNotBlank(search)){
            wrapper.like(Score::getCourseName, search);
        }else if (StrUtil.isNotBlank(studentName)){
            wrapper.like(Score::getStudentName, studentName);
        }
        Page<Score> scorePage = scoreMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(scorePage);
    }

    @PutMapping
    public Result<?> update(@RequestBody Score score){
        score.setUpdateTime(new DateTool().FormatCurrentTime());
        scoreMapper.updateById(score);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id){
        scoreMapper.deleteById(id);
        return Result.success();
    }


}
