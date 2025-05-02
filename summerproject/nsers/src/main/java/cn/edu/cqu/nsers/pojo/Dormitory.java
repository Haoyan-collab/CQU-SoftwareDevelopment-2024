package cn.edu.cqu.nsers.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.annotations.Select;

@Data
@TableName("dormitory")
@ApiModel(value = "宿舍")
public class Dormitory {
    @TableId(type = IdType.AUTO)
    private Integer dormitoryId;       //宿舍ID
    private String dormitoryGarden;    //宿舍所属园区
    private String dormitoryNumber;    //宿舍是园区几栋
    private String dormitoryRoom;      //宿舍是几栋的哪个宿舍
    private Integer dormitoryCnt;      //宿舍目前已入住（注册）人数


}
