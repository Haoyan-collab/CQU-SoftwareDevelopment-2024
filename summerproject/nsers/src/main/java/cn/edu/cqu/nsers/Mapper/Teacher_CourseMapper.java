package cn.edu.cqu.nsers.Mapper;

import cn.edu.cqu.nsers.pojo.Teacher_Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface Teacher_CourseMapper extends BaseMapper<Teacher_Course> {
    @Select("SELECT * FROM teacher_course WHERE cid = #{cid} AND tid = #{tid}")
    Teacher_Course getRelation(@Param("cid") Integer cid, @Param("tid") Integer tid);

    @Update("UPDATE teacher_course SET cnt = #{cnt} WHERE cid = #{cid} AND tid = #{tid}")
    Integer updateCntById(@Param("cid") Integer cid, @Param("tid") Integer tid, @Param("cnt") Integer cnt);
}
