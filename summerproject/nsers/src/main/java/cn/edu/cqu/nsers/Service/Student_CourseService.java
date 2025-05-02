package cn.edu.cqu.nsers.Service;

import cn.edu.cqu.nsers.pojo.Student_Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Student_CourseService extends IService<Student_Course> {
    Student_Course getRelation(@Param("sid") Integer sid, @Param("cid") Integer cid);
}
