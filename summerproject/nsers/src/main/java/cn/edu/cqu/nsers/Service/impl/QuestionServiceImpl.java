package cn.edu.cqu.nsers.Service.impl;

import cn.edu.cqu.nsers.Mapper.QuestionMapper;
import cn.edu.cqu.nsers.Service.QuestionService;
import cn.edu.cqu.nsers.pojo.Question;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
}
