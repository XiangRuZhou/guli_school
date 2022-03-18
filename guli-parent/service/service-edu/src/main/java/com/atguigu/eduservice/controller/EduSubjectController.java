package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultMsg;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zhouXiangRu
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation("添加课程分类")
    @PostMapping("/addSubject")
    public ResultMsg addSubject(@ApiParam(name = "file",value = "file",required = true)
                                @RequestBody MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return null;
    }

    @ApiOperation("获取课程分类列表")
    @GetMapping("/getAllSubject")
    public ResultMsg getAllSubject(){
        List<EduSubject> dataList = eduSubjectService.getAllSubject();
        return ResultMsg.ok().data("item",dataList);
    }

}

