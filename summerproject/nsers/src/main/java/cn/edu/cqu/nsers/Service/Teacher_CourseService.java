package cn.edu.cqu.nsers.Service;

import cn.edu.cqu.nsers.pojo.Teacher_Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface Teacher_CourseService extends IService<Teacher_Course> {
    Teacher_Course getRelation(@Param("cid") Integer cid, @Param("tid") Integer tid);

    Integer updateCntById(@Param("cid") Integer cid, @Param("tid") Integer tid, @Param("cnt") Integer cnt);
}
