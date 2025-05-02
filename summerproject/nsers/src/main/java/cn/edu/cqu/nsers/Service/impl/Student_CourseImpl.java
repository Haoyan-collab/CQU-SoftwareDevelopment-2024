package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.Student_CourseMapper;
import cn.edu.cqu.nsers.Service.Student_CourseService;
import cn.edu.cqu.nsers.pojo.Student_Course;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Student_CourseImpl extends ServiceImpl<Student_CourseMapper, Student_Course> implements Student_CourseService {
    @Autowired
    Student_CourseMapper student_courseMapper;


    @Override
    public Student_Course getRelation(@Param("sid") Integer sid, @Param("cid") Integer cid)
     {
         return student_courseMapper.getRelation(sid,cid);
     }
}
