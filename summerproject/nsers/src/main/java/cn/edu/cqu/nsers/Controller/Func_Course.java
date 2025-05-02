package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Mapper.GradeMapper;
import cn.edu.cqu.nsers.Service.*;
import cn.edu.cqu.nsers.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/functionPage")//功能页面
@Api(value = "功能-选课")
public class Func_Course {
    @Autowired
    public StudentService studentService;
    @Autowired
    public CourseService courseService;
    @Autowired
    public TeacherService teacherService;
    @Autowired
    public Teacher_CourseService teacher_courseService;
    @Autowired
    public Student_CourseService student_courseService;

    @Autowired
    public ClassroomService classroomService;

    @Autowired
    public GradeMapper gradeMapper;

    @GetMapping("/showCoursePage")    //Get到查课时一开始显示所有课的page
    @ApiOperation(value = "展示查课")
    public String showCoursePage() {
        return "showCoursePage";
    }

    @PostMapping("/showCourse")     //在查课页面一开始展示所有课程信息+授课教师，向前端返回一个responseEntity，返回课程属性以及教师姓名
    @ApiOperation(value = "回传查课内容")
    public ResponseEntity<Map<String, Object>> showCourse()
    {
        Map<String, Object> response = new HashMap<>();
        List<Classroom> course = classroomService.list();
        Map<Integer, List<String>> CourseWithTeacher = new HashMap<>();
        //put老师的名字进值里
        for (int i = 0; i < course.size(); i++)
        {
            QueryWrapper<Teacher_Course> teacher_courseQueryWrapper = new QueryWrapper<>();
            Classroom course1 = course.get(i);
            int cid = course1.getCid();
            teacher_courseQueryWrapper.eq("cid", cid);
            List<Teacher_Course> teacher_courseList = teacher_courseService.list(teacher_courseQueryWrapper);
            List<String> tnamelist = new ArrayList<>();
            for (Teacher_Course teacher_course : teacher_courseList) {
                Integer tid = teacher_course.getTid();
                Teacher teacher1 = teacherService.getById(tid);
                tnamelist.add(teacher1.getTname());
            }
            //put课程剩余的属性进值里
            tnamelist.add("division");
            tnamelist.add(course1.getCname());
            tnamelist.add(course1.getCaddress());
            tnamelist.add(course1.getCtime());
            CourseWithTeacher.put(i, tnamelist);
        }
        response.put("CourseWithTeacher", CourseWithTeacher);
        response.put("success",true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/searchCourse")       //查课时根据学生搜索的课程名称向前端返回课程信息+授课教师
    @ApiOperation(value = "查询某一个课")
    public ResponseEntity<Map<String, Object>> getCourse(@RequestParam("cname") String cname)
    {
        Map<String,Object> response = new HashMap<>();
        List<String> CourseWithTeacher = new ArrayList<>();
        QueryWrapper<Classroom> courseQueryWrapper = new QueryWrapper<>();

        courseQueryWrapper.eq("cname", cname);
        Classroom course = classroomService.getOne(courseQueryWrapper);
        QueryWrapper<Teacher_Course> teacher_courseQueryWrapper = new QueryWrapper<>();
        teacher_courseQueryWrapper.eq("cid", course.getCid());
        List<Teacher_Course> teacher_courseList = teacher_courseService.list(teacher_courseQueryWrapper);

        //构建老师-课程对应关系
        for (int j = 0; j < teacher_courseList.size(); j++)
        {
            Teacher_Course teacher_course = teacher_courseList.get(j);
            Integer tid = teacher_course.getTid();
            Teacher teacher = teacherService.getById(tid);
            CourseWithTeacher.add(teacher.getTname());
        }

        //把课程信息传入
        CourseWithTeacher.add("division");
        CourseWithTeacher.add(course.getCaddress());
        CourseWithTeacher.add(course.getCtime());

        response.put("CourseWithTeacher", CourseWithTeacher);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/courseDetailsPage")     //Get到在选课时点进课程后展示详情，包括该课程老师信息，以及该老师是否可选（人满了/已选/可选）
    @ApiOperation(value = "展示课程的老师状态")
    public String courseDetailsPage() {
        return "courseDetailsPage";
    }

    @GetMapping("/selectCoursePage")       //Get到在选课时一开始展示所有课程及其状态的界面（已选满/可选/已选）
    @ApiOperation(value = "展示课程状态")
    public String showSelectCoursePage() {
        return "selectCoursePage";
    }

    @PostMapping("/showSelectCourse1")      //在选课页面一开始展示所有课，可能是已选、已选满或可选
    @ApiOperation(value = "展示选课页面")
    public ResponseEntity<Map<String, Object>> selectCourse(@RequestParam("sid") Integer sid) {
        Map<String, Object> response = new HashMap<>();
        Map<Integer,List<String>> CourseStatus = new HashMap<>();
        Student student = studentService.getById(sid);
        int mid = student.getMid();
        List<Classroom> courseList = classroomService.list();
        List<Classroom> course = new ArrayList<>();
        for(int i=0;i<courseList.size();i++)
        {
            Classroom course1 = courseList.get(i);
            if(course1.getCmid1()==mid||course1.getCmid2()==mid||course1.getCmid1()==8)
            {
                course.add(course1);
            }
        }
        //判断这位学生每门课程的选课情况
        for(int i = 0; i < course.size(); i++)
        {
            Classroom course1 = course.get(i);
            int cid = course1.getCid();
            List<String>Info = new ArrayList<>();
            Info.add(course1.getCname());
            Info.add(course1.getCaddress());
            Info.add(course1.getCtime());
            Student_Course student_course = student_courseService.getRelation(sid,cid);
            if(student_course!=null)    //学生已经选了该门课
            {
                Info.add("已选");
            }
            else
            {
                if(course1.getCcnt()<course1.getCnumber())  //学生未选该门课，且该门课人数还没满
                {
                    Info.add("可选");
                }
                else
                {
                    Info.add("已选满");              //学生未选该门课，但该门课人数已满
                }
            }
            CourseStatus.put(i,Info);
        }
        response.put("CourseStatus", CourseStatus);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/showSelectCourse2")      //点击要选的课程后展示哪些老师教这门课
    @ApiOperation(value = "展示选课老师状态")
    public ResponseEntity<Map<String, Object>> selectTeacher(@RequestParam("sid") Integer sid,
                                                             @RequestParam("cname") String cname)
    {
        System.out.println("!!!!!!!!!"+cname);
        Map<String, Object> response = new HashMap<>();
        List<String> TeacherStatus = new ArrayList<>();
        QueryWrapper<Classroom> courseQueryWrapper = new QueryWrapper<>();

        //查找课程信息和对应老师
        courseQueryWrapper.eq("cname", cname);
        Classroom course = classroomService.getOne(courseQueryWrapper);
        QueryWrapper<Teacher_Course> teacher_courseQueryWrapper = new QueryWrapper<>();
        int cid = course.getCid();
        teacher_courseQueryWrapper.eq("cid", cid);
        List<Teacher_Course> teacher_courseList = teacher_courseService.list(teacher_courseQueryWrapper);

        //put课程信息
        TeacherStatus.add(course.getCaddress());
        TeacherStatus.add(course.getCtime());

        //构建老师-课程对应关系
        for (int j = 0; j < teacher_courseList.size(); j++)
        {
            Teacher_Course teacher_course = teacher_courseList.get(j);
            Integer tid1 = teacher_course.getTid();
            Teacher teacher = teacherService.getById(tid1);
            TeacherStatus.add(teacher.getTname());
            Student_Course student_course = student_courseService.getRelation(sid,cid);
            if(student_course != null)    //学生已经选择该老师的该门课
            {
                Integer tid2 = student_course.getTid();
                if(Objects.equals(tid1, tid2))
                {
                    TeacherStatus.add("已选");
                }
                else
                {
                    if(teacher_course.getCnt()<teacher_course.getNum())
                    {
                        TeacherStatus.add("已选同类课程");
                    }
                    else
                    {
                        TeacherStatus.add("已选同类课程，课程已选满");
                    }
                }
            }
            else
            {
                if(teacher_course.getCnt()<teacher_course.getNum())
                {
                    TeacherStatus.add("可选");
                }
                else
                {
                    TeacherStatus.add("课程已选满");
                }
            }
        }
        TeacherStatus.add("Border");
        TeacherStatus.add("BorderEnd");
        response.put("TeacherStatus", TeacherStatus);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/showSelectCourse3")      //执行选课操作
    @ApiOperation(value = "执行选课")
    public ResponseEntity<Map<String,Object>> select(@RequestParam("sid") Integer sid,
                                                     @RequestParam("tname") String tname,
                                                     @RequestParam("cname") String cname)
    {
        Map<String, Object> response = new HashMap<>();
        QueryWrapper<Classroom> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cname", cname);

        //更新该课程的已选人数
        Classroom course = classroomService.getOne(courseQueryWrapper);
        course.setCcnt(course.getCcnt() + 1);
        classroomService.updateById(course);
        int cid = course.getCid();

        //更新该老师这门课的学生 +1
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("tname", tname);
        Teacher teacher = teacherService.getOne(teacherQueryWrapper);
        int tid = teacher.getTid();
        Teacher_Course teacher_course = teacher_courseService.getRelation(cid, tid);
        int total = teacher_courseService.updateCntById(cid,tid,teacher_course.getCnt() + 1);

        //插入成绩初始记录
        Grade grade = new Grade();
        grade.setCid(cid);
        grade.setSid(sid);
        grade.setTid(tid);
        gradeMapper.insert(grade);
        //记录该学生选了这个老师的这门课
        Student_Course student_course = new Student_Course();
        student_course.setSid(sid);
        student_course.setCid(cid);
        student_course.setTid(tid);
        student_courseService.save(student_course);
        response.put("success", true);
        response.put("message", "选课成功！");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/searchSelectCourse")     //选课时根据学生搜索的课程名称返回课程信息+授课教师
    @ApiOperation(value = "选课搜索老师")
    public ResponseEntity<Map<String, Object>> Search(@RequestParam("sid") Integer sid,
                                                      @RequestParam("cname") String cname) {
        Map<String, Object> response = new HashMap<>();
//        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
//        courseQueryWrapper.eq("cname", cname);

        Student student = studentService.getById(sid);
        int mid = student.getMid();
        List<Classroom> courseList = classroomService.list();
        List<Classroom> course = new ArrayList<>();
        for(int i=0;i<courseList.size();i++)
        {
            Classroom course1 = courseList.get(i);
            if(course1.getCmid1()==mid||course1.getCmid2()==mid||course1.getCmid1()==8)
            {
                course.add(course1);
            }
        }

        List<String>Info = new ArrayList<>();
        boolean flag = false;
        int cid = 0;
        Classroom course1 = null;
        //查找课程
        for(int i=0;i<course.size();i++)
        {
            course1 = course.get(i);
            if(course1.getCname().equals(cname))
            {
                //put课程信息，当且仅当该课程可被此学生的专业选
                Info.add(course1.getCaddress());
                Info.add(course1.getCtime());
                cid = course1.getCid();
                flag = true;
                break;
            }
        }
        if(flag)
        {
            //判断该学生搜索的课程对应老师的选课情况
            Student_Course student_course = student_courseService.getRelation(sid,cid);
            if(student_course!=null)    //学生已经选了该门课
            {
                Info.add("已选");
            }
            else
            {
                if(course1.getCcnt() < course1.getCnumber())  //学生未选该门课，且该门课人数还没满
                {
                    Info.add("可选");
                }
                else
                {
                    Info.add("已选满");              //学生未选该门课，但该门课人数已满
                }
            }

        }
        response.put("success",true);
        response.put("Info",Info);
        return ResponseEntity.ok(response);
    }
}
