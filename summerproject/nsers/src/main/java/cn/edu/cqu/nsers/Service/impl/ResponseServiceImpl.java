package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.ResponseMapper;
import cn.edu.cqu.nsers.Service.ResponseService;
import cn.edu.cqu.nsers.pojo.Response;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl extends ServiceImpl<ResponseMapper, Response> implements ResponseService {
}
