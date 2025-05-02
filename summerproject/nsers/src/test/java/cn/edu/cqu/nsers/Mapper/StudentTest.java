package cn.edu.cqu.nsers.Mapper;

import cn.edu.cqu.nsers.Service.CourseService;
import cn.edu.cqu.nsers.pojo.Student;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentTest {
    @Autowired
    public StudentMapper studentMapper;

    @Autowired
    public CourseService courseService;

    @Test
    public void getList()
    {
        List<Student> list=studentMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void courseCnt()
    {
        long sum=courseService.count();
        System.out.println("Total course is: "+sum);
    }
}
