package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.CourseService;
import cn.edu.cqu.nsers.Service.SchoolemailService;
import cn.edu.cqu.nsers.Service.StudentService;
import cn.edu.cqu.nsers.Service.Student_CourseService;
import cn.edu.cqu.nsers.pojo.Course;
import cn.edu.cqu.nsers.pojo.Schoolemail;
import cn.edu.cqu.nsers.pojo.Student;
import cn.edu.cqu.nsers.pojo.Student_Course;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/schoolemail")
@Api(value = "校园邮箱")
public class SchoolEmailController {
    @Autowired
    public StudentService studentService;
    @Autowired
    public SchoolemailService schoolemailService;
    @Autowired
    public CourseService courseService;
    @Autowired
    public Student_CourseService student_courseService;

    @GetMapping("/applyPage")
    @ApiOperation(value = "展示申请校园邮箱页面")
    public String showApplyPage()
    {
        return "emailApplyPage";
    }

    @PostMapping("/apply")      //第一次登录后，申请校园邮箱并设置密码
    @ApiOperation(value = "首次登录设置密码")
    public ResponseEntity<Map<String,Object>> Apply(@RequestParam("sid") Integer sid,
                                                    @RequestParam("saname") String saname,
                                                    @RequestParam("spassword1") String spassword1,
                                                    @RequestParam("spassword2") String spassword2)
    {
        Map<String, Object> response = new HashMap<>();
        Student student = studentService.getById(sid);
        student.setSaname(saname);
        Schoolemail schoolemail = schoolemailService.getById(student.getEid());
        schoolemail.setEpassword(spassword1);
        schoolemail.setEbool(true);
        schoolemailService.updateById(schoolemail);
        response.put("success", true);
        response.put("message", "密码设置成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/loginPage")
    @ApiOperation(value = "展示登录校园邮箱页面")
    public String showLoginPage()
    {
        return "emailLoginPage";
    }

    @PostMapping("/login")      //第二次及以后登录后，点了邮件按钮自动跳到登录邮箱界面，输入密码并判断是否正确
    @ApiOperation(value = "第二次及以后登录时核对密码")
    public ResponseEntity<Map<String, Object>> Login(@RequestParam("epath") String epath,
                                                     @RequestParam("epassword") String epassword
                                                     )
    {
        Map<String, Object> response = new HashMap<>();
        QueryWrapper<Schoolemail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("epath", epath);
        Schoolemail schoolemail = schoolemailService.getOne(queryWrapper);
        if(schoolemail == null || !schoolemail.getEpassword().equals(epassword))
        {
            response.put("success", false);
            response.put("message", "账号或密码错误!");
            return ResponseEntity.status(401).body(response);
        }
        response.put("success", true);
        response.put("message", "登陆成功!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/letterPage")
    @ApiOperation(value = "展示邮件页面")
    public String showLetterPage() {
        return "emailInterfacePage";
    }


    @PostMapping("/letter")     //登录进入邮箱后做个邮箱收件箱页面，根据后端传回的两个boolean值判断是否需显示邮件提醒没有选课或没有上传照片
    @ApiOperation(value = "回传提醒学生未做事项")
    public ResponseEntity<Map<String,Object>> sendLetter(@RequestParam("sid") Integer sid)
    {
        Student student = studentService.getById(sid);
        Map<String, Object> response = new HashMap<>();
        int mid = student.getMid();
        //根据没有完成的事项发送文件
        List<Course>courses = courseService.list();
        List<Course>courseList = new ArrayList<>();
        for(int i=0;i<courses.size();i++)
        {
            Course course = courses.get(i);
            //筛选出可选的专业课
            if(course.getCmid1()==mid||course.getCmid2()==mid||course.getCmid1()==8)
            {
                courseList.add(course);
            }
        }
        boolean hasSelectedAll = true;
        List<String>list = new ArrayList<>();
        for(int i=0;i<courseList.size();i++)
        {
            Course course = courseList.get(i);
            if(course.getCcnt()<course.getCnumber())    //该课程仍有名额
            {
                int cid = course.getCid();
                QueryWrapper<Student_Course> student_courseQueryWrapper = new QueryWrapper<>();
                student_courseQueryWrapper.eq("cid",cid);
                List<Student_Course>student_courseList = student_courseService.list(student_courseQueryWrapper);
                boolean flag = false;
                for (int j=0;j<student_courseList.size();j++)
                {
                    Student_Course student_course = student_courseList.get(j);
                    if (Objects.equals(student_course.getSid(), sid))
                    {
                        flag = true;
                        break;
                    }
                }
                if(!flag)
                {
                    hasSelectedAll = false;
                    list.add(course.getCname());
                }
            }
        }
        response.put("hasSelectedAll",hasSelectedAll);   //bool值
        response.put("list",list);
        if(student.getUpdatePhoto() == 0)
        {
            response.put("hasUpdatePhoto",false);
        }
        else
        {
            response.put("hasUpdatePhoto",true);     //bool值
        }
        response.put("success",true);
        return ResponseEntity.ok(response);
    }
}
