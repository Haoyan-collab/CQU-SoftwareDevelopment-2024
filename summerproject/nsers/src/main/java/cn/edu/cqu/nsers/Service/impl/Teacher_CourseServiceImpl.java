package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.Teacher_CourseMapper;
import cn.edu.cqu.nsers.Service.Teacher_CourseService;
import cn.edu.cqu.nsers.pojo.Teacher_Course;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Teacher_CourseServiceImpl extends ServiceImpl<Teacher_CourseMapper, Teacher_Course> implements Teacher_CourseService {
    @Autowired
    Teacher_CourseMapper teacher_courseMapper;

    @Override
    public Teacher_Course getRelation(@Param("cid") Integer cid, @Param("tid") Integer tid) {
        return teacher_courseMapper.getRelation(cid, tid);
    }


    @Override
    public Integer updateCntById(@Param("cid") Integer cid, @Param("tid") Integer tid, @Param("cnt") Integer cnt) {
        return teacher_courseMapper.updateCntById(cid, tid, cnt);
    }
}
