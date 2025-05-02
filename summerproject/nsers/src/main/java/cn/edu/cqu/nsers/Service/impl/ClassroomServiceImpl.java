package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.ClassroomMapper;
import cn.edu.cqu.nsers.Service.ClassroomService;
import cn.edu.cqu.nsers.pojo.Classroom;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {
}
