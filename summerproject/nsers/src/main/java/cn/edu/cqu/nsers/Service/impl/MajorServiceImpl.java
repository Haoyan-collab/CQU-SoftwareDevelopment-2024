package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.MajorMapper;
import cn.edu.cqu.nsers.Service.MajorService;
import cn.edu.cqu.nsers.pojo.Major;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {
}
