package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("department")
@ApiModel(value = "院系")
public class Department {
    @TableId(type = IdType.AUTO)
    private Integer departmentId;       //院系ID
    private String departmentName;      //院系名称
    private Integer departmentCnt;      //院系总人数
}
