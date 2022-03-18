package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.Subject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zhouXiangRu
 * @since 2022-03-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectMapper subjectMapper;

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //获取文件流
            InputStream inputStream = file.getInputStream();
            //一行一行的读取，指定class去读，读取第一个sheet页，文件流会自动关闭
            EasyExcel.read(inputStream, Subject.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EduSubject> getAllSubject() {
        List<EduSubject> subjects = subjectMapper.selectList(null);
        if(subjects==null||subjects.size()==0){
            return subjects;
        }
        List<EduSubject> rootSubject = new ArrayList<>();
        for (EduSubject subject : subjects) {   //获取根节点
            if(subject.getParentId().equals("0")){
                rootSubject.add(subject);
            }
        }

        for (EduSubject parentSubject : rootSubject) {
            String id = parentSubject.getId();
            List<EduSubject> childList = getChild(id,subjects); //根据根节点获取子节点列表
            parentSubject.setChildren(childList);
        }
        return rootSubject;
    }

    //根据父节点ID获取子节点
    private List<EduSubject> getChild(String id, List<EduSubject> subjects) {
        List<EduSubject> childrenList = new ArrayList<>();//初始化子节点
        for (EduSubject subject : subjects) {
            if(subject.getParentId().equals(id)){
                childrenList.add(subject);
            }
        }

        for (EduSubject eduSubject : childrenList) {
            this.getChild(eduSubject.getId(),childrenList);
        }
        return childrenList;
    }
}
