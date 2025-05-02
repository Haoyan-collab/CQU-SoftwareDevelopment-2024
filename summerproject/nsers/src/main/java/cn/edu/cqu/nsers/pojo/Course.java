package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("course")
@ApiModel(value = "课程")
public class Course {
    @TableId(type = IdType.AUTO)
    private Integer cid;                //课程ID
    private String cname;               //课程名称
    private String caddress;            //上课地点
    private String ctime;               //上课时间
    private Integer cnumber;            //课程最大总人数
    private Integer ccnt;               //课程已报名人数
    private Integer cmid1;
    private Integer cmid2;
}
