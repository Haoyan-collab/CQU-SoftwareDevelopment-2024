package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Mapper.ClassroomMapper;
import cn.edu.cqu.nsers.Mapper.Teacher_CourseMapper;
import cn.edu.cqu.nsers.Service.*;
import cn.edu.cqu.nsers.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("adminSystem")
public class Administrator {
    @Autowired
    GradeService gradeService;

    @Autowired
    ClassroomService classroomService;

    @Autowired
    StudentService studentService;

    @Autowired
    Teacher_CourseService teacher_courseService;

    @Autowired
    TeacherService teacherService;
    @Autowired
    Student_CourseService student_courseService;

    @Autowired
    ClassroomMapper classroomMapper;

    @Autowired
    Teacher_CourseMapper teacher_courseMapper;

    @GetMapping("/adminDashboard")
    public String showAdminDashboard() {
        return "adminDashboard"; // 返回模板名称，不需要加 .html 后缀
    }

    @GetMapping("searchGradesPage")
    public String showGrades()
    {
        return "searchGradesPage";
    }

    @GetMapping("searchClassroomPage")
    public String showClassroomGrade()
    {
        return "searchClassroomPage";
    }

    @PostMapping("searchGradesById")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchGradesById(@RequestParam("sid") Integer sid) {
        Map<String, Object> response = new HashMap<>();

        List<Grade> grades = gradeService.list(new QueryWrapper<Grade>().eq("sid", sid));
        if (grades.isEmpty()) {
            response.put("error", "未找到该学生的成绩记录");
            return ResponseEntity.ok(response);
        }

        String sname = studentService.getById(sid).getSname();
        response.put("studentid", sid);
        response.put("studentname", sname);

        List<Map<String, Object>> gradeDetails = new ArrayList<>();
        for (Grade grade : grades) {
            int cid = grade.getCid();
            String cname = classroomService.getById(cid).getCname();

            Map<String, Object> gradeDetail = new HashMap<>();
            gradeDetail.put("classname", cname);
            gradeDetail.put("regular", grade.getRegular());
            gradeDetail.put("midterm", grade.getMidterm());
            gradeDetail.put("lab", grade.getLab());
            gradeDetail.put("end", grade.getEnd());
            gradeDetail.put("total", grade.getTotal());

            gradeDetails.add(gradeDetail);
        }

        response.put("grades", gradeDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping("searchGradesByName")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchGradesByName(@RequestParam("sname") String sname) {
        Map<String, Object> response = new HashMap<>();
        int sid = studentService.getOne(new QueryWrapper<Student>().eq("sname",sname)).getSid();
        List<Grade> grades = gradeService.list(new QueryWrapper<Grade>().eq("sid", sid));
        if (grades.isEmpty()) {
            response.put("error", "未找到该学生的成绩记录");
            return ResponseEntity.ok(response);
        }

        response.put("studentid", sid);
        response.put("studentname",sname);
        List<Map<String, Object>> gradeDetails = new ArrayList<>();
        for (Grade grade : grades) {
            int cid = grade.getCid();
            String cname = classroomService.getById(cid).getCname();

            Map<String, Object> gradeDetail = new HashMap<>();
            gradeDetail.put("classname", cname);
            gradeDetail.put("regular", grade.getRegular());
            gradeDetail.put("midterm", grade.getMidterm());
            gradeDetail.put("lab", grade.getLab());
            gradeDetail.put("end", grade.getEnd());
            gradeDetail.put("total", grade.getTotal());

            gradeDetails.add(gradeDetail);
        }

        response.put("grades", gradeDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping("searchGradesOfClassroom")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> showGradesByCid(@RequestParam("classroomid") Integer classroomid)
    {
        Teacher_Course teacher_course = teacher_courseService.getById(classroomid);
        int tid = teacher_course.getTid();
        int cid = teacher_course.getCid();
        Map<String,Object>response = new HashMap<>();
        response.put("classname",classroomService.getById(cid).getCname());
        response.put("teachername",teacherService.getById(tid).getTname());
        List<Map<String,Object>>studentDetails = new ArrayList<>();
        List<Student_Course>student_courses = student_courseService.list(new QueryWrapper<Student_Course>().eq("cid",cid));
        System.out.println("student_courseList:"+student_courses);
        for (Student_Course student_course : student_courses)
        {
            if (student_course.getTid()==tid)
            {
                Map<String,Object>studentDetail = new HashMap<>();
                Student student = studentService.getById(student_course.getSid());
                System.out.println("student:"+student);
                studentDetail.put("studentid",student.getSid());
                studentDetail.put("studentname",student.getSname());
                Grade grade = gradeService.getRelation(student.getSid(),cid,tid);
                System.out.println("grade!!!!!"+grade);
                studentDetail.put("regular", grade.getRegular());
                studentDetail.put("midterm", grade.getMidterm());
                studentDetail.put("lab", grade.getLab());
                studentDetail.put("end", grade.getEnd());
                studentDetail.put("total", grade.getTotal());
                System.out.println("studentsdetail!!!!!!!!!"+studentDetail);
                studentDetails.add(studentDetail);
            }
        }
        response.put("students", studentDetails);
        System.out.println(studentDetails);
        return ResponseEntity.ok(response);
    }

    @GetMapping("addClassPage")
    public String adjustClass()
    {
        return "addClassPage";
    }

    @PostMapping("addClass")//只能添加不存在的课程
    @ResponseBody
    public ResponseEntity<Map<String,Object>> addClass(@RequestParam("classname") String classname,
                                                       @RequestParam("ctime") String ctime)
    {
        Map<String,Object>response = new HashMap<>();
        if (classroomService.count(new QueryWrapper<Classroom>().eq("cname",classname))>0)
        {
            response.put("success",false);
            response.put("message","课程已存在！请重新输入");
        }
        else
        {
            Classroom classroom = new Classroom();
            classroom.setCtime(ctime);
            classroom.setCname(classname);
            if (classroomMapper.insert(classroom)>0)
            {
                response.put("success",true);
                response.put("message","新课程插入成功！请为课程分配教师与课程班");
            }
            else
            {
                response.put("success",false);
                response.put("message","插入失败！");
            }
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("getTeacherInfo")//分配老师时只能分配已存在的老师
    public ResponseEntity<Map<String,Object>> getTeacher()
    {
        Map<String,Object>response = new HashMap<>();
        List<Teacher>teachers = teacherService.list();
        List<String>teacherName = new ArrayList<>();
        for (Teacher teacher:teachers)
        {
            teacherName.add(teacher.getTname());
        }
        response.put("teacherNames",teacherName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("allocateTeacher")//为新创建的课程分配老师，此时需要传新创的课程名以在数据库里找到新课的cid
    @ResponseBody
    public ResponseEntity<Map<String,Object>>allocateTeacher(@RequestParam("tname")String tname,
                                                             @RequestParam("classname") String classname)
    {
        Map<String,Object>response = new HashMap<>();
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("tname",tname));
        int tid = teacher.getTid();
        Classroom classroom = classroomService.getOne(new QueryWrapper<Classroom>().eq("cname",classname));
        int cid = classroom.getCid();
        Teacher_Course teacher_course = new Teacher_Course();
        teacher_course.setCid(cid);
        teacher_course.setTid(tid);
        if (teacher_courseMapper.insert(teacher_course)>0)
        {
            response.put("success",true);
            response.put("message","课程班分配完毕！");
        }
        else
        {
            response.put("success",false);
            response.put("message","课程班分配失败！");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("searchClassPage")
    public String showClassGrades()
    {
        return "searchClassPage";
    }

    @PostMapping("searchClassGrades")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchClass(@RequestBody Map<String, String> request) {
        String cname = request.get("cname");
        int cid = classroomService.getOne(new QueryWrapper<Classroom>().eq("cname", cname)).getCid();
        List<Teacher_Course> teacher_courses = teacher_courseService.list(new QueryWrapper<Teacher_Course>().eq("cid", cid));
        List<Map<String, Object>> studentDetails = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Teacher_Course teacher_course : teacher_courses) {
            int tid = teacher_course.getTid();
            String tname = teacherService.getById(tid).getTname();
            List<Grade> grades = gradeService.getList(cid, tid);
            for (Grade grade : grades) {
                Map<String, Object> info = new HashMap<>();
                info.put("teacher", tname);
                info.put("studentid", grade.getSid());
                info.put("studentname", studentService.getById(grade.getSid()).getSname());
                info.put("regular", grade.getRegular());
                info.put("midterm", grade.getMidterm());
                info.put("lab", grade.getLab());
                info.put("end", grade.getEnd());
                info.put("total", grade.getTotal());
                studentDetails.add(info);
            }
        }
        response.put("allInfo", studentDetails);
        return ResponseEntity.ok(response);
    }


}
