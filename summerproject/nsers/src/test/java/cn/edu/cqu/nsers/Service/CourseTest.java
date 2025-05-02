package cn.edu.cqu.nsers.Service;

import cn.edu.cqu.nsers.pojo.Course;
import cn.edu.cqu.nsers.pojo.Dormitory;
import cn.edu.cqu.nsers.pojo.Student;
import cn.edu.cqu.nsers.pojo.Teacher_Course;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseTest {
    @Autowired
    public CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormitoryService dormitoryService;
    @Autowired
    public Teacher_CourseService teacher_courseService;
    @Test
    public void getCourse()
    {
        Course course = courseService.getById(33);
        System.out.println(course.getCtime());
    }

    @Test
    public void getById()
    {
        Student student = studentService.getById(20240005);
        System.out.println(student);
    }

    @Test
    public void getDormitory()
    {
        QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dormitory_garden","梅园");
        Dormitory dormitory = dormitoryService.getOne(queryWrapper,false);
        System.out.println(dormitory);
    }
//    @Test
//    public void update()
//    {
//        Teacher_Course teacher_course = new Teacher_Course();
//        int rowaffected = teacher_courseService.updateCntById(5,1,100);
//        List<Teacher_Course>teacher_courseList = teacher_courseService.list();
//        System.out.println(teacher_courseList);
//        System.out.println(rowaffected);
//    }

}
