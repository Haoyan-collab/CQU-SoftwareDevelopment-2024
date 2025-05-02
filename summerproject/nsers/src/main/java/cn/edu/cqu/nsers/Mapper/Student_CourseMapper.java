package cn.edu.cqu.nsers.Mapper;

import cn.edu.cqu.nsers.pojo.Student_Course;
import cn.edu.cqu.nsers.pojo.Teacher_Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Student_CourseMapper extends BaseMapper<Student_Course> {
    @Select("SELECT * FROM student_course WHERE sid = #{sid} AND cid = #{cid}")
    Student_Course getRelation(@Param("sid") Integer sid, @Param("cid") Integer cid);
}
