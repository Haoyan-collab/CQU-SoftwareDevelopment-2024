package cn.edu.cqu.nsers.Service;

import cn.edu.cqu.nsers.pojo.Dormitory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface DormitoryService extends IService<Dormitory> {
    Integer getSumOfGarden(String dormitory_garden);
}
