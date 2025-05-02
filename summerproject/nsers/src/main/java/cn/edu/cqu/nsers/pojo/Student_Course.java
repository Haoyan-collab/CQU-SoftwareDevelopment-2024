package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("student_course")
@ApiModel(value = "学生-课程关系")
public class Student_Course {
    Integer sid;
    Integer cid;
    Integer tid;
}
