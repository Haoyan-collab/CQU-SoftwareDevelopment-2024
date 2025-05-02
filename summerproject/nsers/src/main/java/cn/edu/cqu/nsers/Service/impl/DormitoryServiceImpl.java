package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.DormitoryMapper;
import cn.edu.cqu.nsers.Service.DormitoryService;
import cn.edu.cqu.nsers.pojo.Dormitory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Override
    public Integer getSumOfGarden(String dormitory_garden) {
        return dormitoryMapper.getSumOfGarden(dormitory_garden);
    }

}
