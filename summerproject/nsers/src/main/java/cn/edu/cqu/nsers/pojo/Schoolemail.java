package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("schoolemail")
@ApiModel(value = "校园邮箱")
public class Schoolemail {
    @TableId(type = IdType.AUTO)
    private int eid;            //校园邮箱id
    private boolean ebool;      //该校园邮箱是否被申请
    private String epassword;   //校园邮箱密码
    private String epath;       //校园邮箱地址
}
