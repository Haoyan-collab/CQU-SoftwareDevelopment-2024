package cn.edu.cqu.nsers.Service;

import cn.edu.cqu.nsers.pojo.Grade;
import cn.edu.cqu.nsers.pojo.Student_Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeService extends IService<Grade> {
    Grade getRelation(@Param("sid") Integer sid, @Param("cid") Integer cid, @Param("tid") Integer tid);

    List<Grade> getList(@Param("cid")Integer cid, @Param("tid") Integer tid);
}
