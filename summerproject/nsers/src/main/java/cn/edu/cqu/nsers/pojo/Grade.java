package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
@TableName("grade")
@ApiModel(value = "成绩")
public class Grade {
    @TableId
    private Integer gid;

    private Integer sid;

    private Integer cid;
    private Integer regular;
    private Integer midterm;
    private Integer lab;
    private Integer end;
    private Integer total;
    private Integer tid;
}
