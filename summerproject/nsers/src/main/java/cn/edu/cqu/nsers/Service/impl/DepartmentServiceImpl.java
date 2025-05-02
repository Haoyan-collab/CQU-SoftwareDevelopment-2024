package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.DepartmentMapper;
import cn.edu.cqu.nsers.Service.DepartmentService;
import cn.edu.cqu.nsers.pojo.Department;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
