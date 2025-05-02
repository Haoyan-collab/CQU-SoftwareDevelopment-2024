package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.GradeMapper;
import cn.edu.cqu.nsers.Mapper.Student_CourseMapper;
import cn.edu.cqu.nsers.Service.GradeService;
import cn.edu.cqu.nsers.pojo.Grade;
import cn.edu.cqu.nsers.pojo.Student_Course;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Autowired
    GradeMapper gradeMapper;


    @Override
    public Grade getRelation(@Param("sid") Integer sid, @Param("cid") Integer cid, @Param("tid") Integer tid)
    {
        return gradeMapper.getRelation(sid,cid,tid);
    }

    @Override
    public List<Grade> getList(@Param("cid")Integer cid, @Param("tid") Integer tid)
    {
        return gradeMapper.getList(cid,tid);
    }
}
