package cn.edu.cqu.nsers.Mapper;

import cn.edu.cqu.nsers.pojo.Grade;
import cn.edu.cqu.nsers.pojo.Student_Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hibernate.mapping.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
    @Select("SELECT * FROM grade WHERE sid = #{sid} AND cid = #{cid} AND tid = #{tid}")
    Grade getRelation(@Param("sid") Integer sid, @Param("cid") Integer cid, @Param("tid") Integer tid);

    @Select("SELECT * FROM grade WHERE cid = #{cid} AND tid = #{tid}")
    List<Grade> getList(@Param("cid")Integer cid,@Param("tid") Integer tid);
}