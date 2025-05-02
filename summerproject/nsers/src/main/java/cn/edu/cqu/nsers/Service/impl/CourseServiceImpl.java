package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.CourseMapper;
import cn.edu.cqu.nsers.Service.CourseService;
import cn.edu.cqu.nsers.pojo.Course;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
