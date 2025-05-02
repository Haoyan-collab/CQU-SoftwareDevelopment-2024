package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("teacher_course")
@ApiModel(value = "老师-课程关系")
public class Teacher_Course {
    private Integer tid;
    private Integer cid;
    private Integer num;
    private Integer cnt;
    @TableId
    private Integer classroomid;
}
