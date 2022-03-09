package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.ResultMsg;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherPageVo;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhouXiangRu
 * @since 2022-03-04
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("获取讲师列表")
    @GetMapping("/getAllTeacher")
    public ResultMsg getAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return ResultMsg.ok().data("items", list);
    }

    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/deleteTeacher/{id}")
    public ResultMsg deleteTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeByIds(Arrays.asList(id.split(",")));
        if (flag) {
            return ResultMsg.ok();
        }
        return ResultMsg.error();
    }

    @ApiOperation("分页查询教师列表")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public ResultMsg getTeacherPage(
            @ApiParam(name = "current", value = "当前页")
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页条数")
            @PathVariable long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        //会将查询到数据封装到eduTeacherPage对象中
        teacherService.page(eduTeacherPage, null);
        Map map = new HashMap<>();
        map.put("total", eduTeacherPage.getTotal());
        map.put("pages", eduTeacherPage.getPages());
        map.put("items", eduTeacherPage.getRecords());
        return ResultMsg.ok().data(map);
    }

    @ApiOperation("根据条件获取讲师分页列表")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public ResultMsg getTeacherPage(
            @ApiParam(name = "current", value = "当前页")
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页条数")
            @PathVariable long limit,
            @RequestBody(required = false) TeacherPageVo teacherPageVo) {
        Page<EduTeacher> page = new Page<>(current, limit);
        teacherService.pageQuery(page, teacherPageVo);
        Map map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("pages", page.getPages());
        map.put("items", page.getRecords());
        return ResultMsg.ok().data(map);
    }

    @ApiOperation("新增讲师")
    @PostMapping("/addTeacher")
    public ResultMsg addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.save(eduTeacher);
        if (flag) {
            return ResultMsg.ok();
        }
        return ResultMsg.error();
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/getTeacger/{id}")
    public ResultMsg getTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        int i = 10/0;
        EduTeacher eduTeacher = teacherService.getById(id);
        return ResultMsg.ok().data("teacher", eduTeacher);
    }

    @ApiOperation("根据ID修改")
    @PostMapping("/modifyTeacher/{id}")
    public ResultMsg modeifyTeacherById(
            @ApiParam(name = "id", value = "讲师ID")
            @PathVariable String id,
            @RequestBody EduTeacher eduTeacher
    ) {
        eduTeacher.setId(id);
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return ResultMsg.ok();
        }
        return ResultMsg.error();
    }
}

