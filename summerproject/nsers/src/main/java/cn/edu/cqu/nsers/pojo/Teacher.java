package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("teacher")
@ApiModel(value = "老师")
public class Teacher {
    @TableId(type = IdType.AUTO)
    private Integer tid;
    private String tname;
    private String toffice;
    private String tphone;
    private String tpassword;
    private String tnumber;
}
