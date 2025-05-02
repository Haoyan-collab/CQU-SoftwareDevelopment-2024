package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.TeacherMapper;
import cn.edu.cqu.nsers.Service.TeacherService;
import cn.edu.cqu.nsers.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
