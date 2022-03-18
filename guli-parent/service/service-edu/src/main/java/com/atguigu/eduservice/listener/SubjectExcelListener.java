package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.Subject;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.EduTeacherException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<Subject> {

    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(Subject excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData==null){
            throw  new EduTeacherException(400,"文件内容为空！");
        }
        //表格中以及分类必须存在，否则不会处理该行数据
        if(excelSubjectData.getOneSubjectName()!=null){
            //判断一级分类是否存在，如果存在则不进行写入操作
            EduSubject oneSubject = exisOneSubject(excelSubjectData.getOneSubjectName());
            if(oneSubject==null){
                EduSubject insertOne = new EduSubject();
                insertOne.setParentId("0");
                insertOne.setTitle(excelSubjectData.getOneSubjectName());
                subjectService.save(insertOne);
            }

            //根据名称获取以及分类的ID，如果一级分类为空，则不进行二级分类的插入
            if(excelSubjectData.getTwoSubjectName()!=null){
                oneSubject = exisOneSubject(excelSubjectData.getOneSubjectName());
                EduSubject twoSubject = exisTwoSubject(excelSubjectData.getTwoSubjectName(),oneSubject.getId());
                if(twoSubject==null){//判断二级分类是否存在，如果存在则不进行写入操作
                    EduSubject insertTwo = new EduSubject();
                    insertTwo.setTitle(excelSubjectData.getTwoSubjectName());
                    insertTwo.setParentId(oneSubject.getId());
                    subjectService.save(insertTwo);
                }
            }

        }

    }

    //判断是否存在二级名称
    private EduSubject exisTwoSubject(String twoSubjectName, String id) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",twoSubjectName);
        queryWrapper.eq("parent_id","id");
        EduSubject two = subjectService.getOne(queryWrapper);
        return two;
    }

    //判断是否存在一级名称
    private EduSubject exisOneSubject(String oneSubjectName) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",oneSubjectName);
        queryWrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(queryWrapper);
        return one;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }
}
