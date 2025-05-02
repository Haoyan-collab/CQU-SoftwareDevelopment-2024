package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("major")
@ApiModel(value = "专业")
public class Major {
    @TableId(type = IdType.AUTO)
    private Integer mid;                //专业ID
    private Integer departmentId;      //外键：引用院系ID
    private String mname;               //专业名称
}
