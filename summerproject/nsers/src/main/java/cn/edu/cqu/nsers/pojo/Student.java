package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("student")
@ApiModel(value = "学生")
public class Student {
    @TableId(type = IdType.AUTO, value = "sid")
    private Integer sid;                        //学生id
    private Integer dormitoryId;                //对应宿舍id
    private Integer eid;                        //对应校园邮箱id
    private Integer mid;                        //对应专业id
    private String spassword;                   //账户密码
    private String sname;                       //学生账户用户名
    private String saname;                      //校园邮箱用户名
    private String spicture;                    //个人照片路径
    private String snumber;                     //身份证号
    private String sphone;                      //电话号码
    private String sprovince;                   //省份
    private String scity;                       //城市
    private String semail;                      //学生自己的邮箱
    private Byte updatePhoto;                   //是否上传个人照片
    private Byte registerAccount;               //是否已注册账户
    private String sgender;                     //性别
}
