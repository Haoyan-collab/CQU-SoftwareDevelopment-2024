package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.StudentMapper;
import cn.edu.cqu.nsers.Service.StudentService;
import cn.edu.cqu.nsers.pojo.Student;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
