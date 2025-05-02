package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("response")
@ApiModel(value = "回答")
public class Response {
    @TableId(type = IdType.AUTO)
    private Integer rid;        //回答id
    private String rname;       //回答表述
}
