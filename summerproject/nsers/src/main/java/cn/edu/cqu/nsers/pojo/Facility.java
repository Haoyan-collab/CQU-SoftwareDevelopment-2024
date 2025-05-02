package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("facilities")
@ApiModel(value = "设施")
public class Facility {
    private Integer fid;
    private String fname;
    private String fkind;
}
