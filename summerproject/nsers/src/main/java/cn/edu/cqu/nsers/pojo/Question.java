package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("question")
@ApiModel(value = "问题")
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer qid;        //问题id
    private Integer rid;        //外键：引用对应回答id
    private String qname;       //问题描述
}
