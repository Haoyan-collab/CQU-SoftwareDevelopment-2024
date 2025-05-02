package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.*;
import cn.edu.cqu.nsers.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Controller
@RequestMapping("/teacherSystem")
@Api(value = "教师端登录")
public class TeacherController {

    @Autowired
    public Teacher_CourseService teacher_courseService;
    @Autowired
    public CourseService courseService;
    @Autowired
    public Student_CourseService student_courseService;
    @Autowired
    public StudentService studentService;
    @Autowired
    public TeacherService teacherService;

    @Autowired
    public ClassroomService classroomService;

    @Autowired
    public GradeService gradeService;

    @GetMapping("teacherManagePage")
    @ApiOperation(value = "展示教师管理页面")
    public String showTeacherManagePage() {
        return "teacherManagePage";
    }

    @GetMapping("teacherCoursePage")
    @ApiOperation(value = "展示教师所教课程页面")
    public String showTeacherCoursePage() {
        return "teacherCoursePage";
    }

    @PostMapping("showYourCourse")      //找到该老师教授的所有课程
    @ApiOperation(value = "回传老师授课信息")
    public ResponseEntity<Map<String, Object>> showYourCourse(@RequestParam("tid") Integer tid)
    {
        Map<String, Object> response = new HashMap<>();
        Map<Integer, List<String>> CourseAndStudent = new HashMap<>();
        QueryWrapper<Teacher_Course> teacher_courseQueryWrapper = new QueryWrapper<>();

        //找到所有对应课程
        teacher_courseQueryWrapper.eq("tid", tid);
        List<Teacher_Course>teacher_courseList = teacher_courseService.list(teacher_courseQueryWrapper);
        System.out.println("list！！！！！！！！！！！！！！！！！！！！！！！！！！！！！"+teacher_courseList);

        for(int i = 0; i < teacher_courseList.size(); i++)
        {
            Teacher_Course teacher_course = teacher_courseList.get(i);
            int cid = teacher_course.getCid();
            Classroom course = classroomService.getById(cid);
            List<String> Info = new ArrayList<>();
            Info.add(course.getCid().toString());
            Info.add(course.getCaddress());
            Info.add(course.getCtime());
            Info.add(course.getCname());
            CourseAndStudent.put(i, Info);
        }
        response.put("success",true);
        response.put("CourseAndStudent", CourseAndStudent);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/showYourStudent")        //找到所有选了该课程的学生
//    @ApiOperation(value = "回传老师所授学生信息")
//    public ResponseEntity<Map<String, Object>> showYourStudent(@RequestParam("tname") String tname,
//                                                              @RequestParam("cname") String cname)
//    {
//        Map<String,Object> response = new HashMap<>();
//        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
//        teacherQueryWrapper.eq("tname",tname);
//        Teacher teacher = teacherService.getOne(teacherQueryWrapper);
//        int tid = teacher.getTid();
//        QueryWrapper<Student_Course> student_courseQueryWrapper = new QueryWrapper<>();
//        student_courseQueryWrapper.eq("tid",tid);
//        List<Student_Course> student_courseList = student_courseService.list(student_courseQueryWrapper);
//        QueryWrapper<Classroom> courseQueryWrapper = new QueryWrapper<>();
//        courseQueryWrapper.eq("cname",cname);
//        Classroom course = classroomService.getOne(courseQueryWrapper);
//        List<String>Info = new ArrayList<>();
//        for(int i = 0; i < student_courseList.size();i++)
//        {
//            Student_Course student_course = student_courseList.get(i);
//            if(Objects.equals(student_course.getCid(), course.getCid()))
//            {
//                Student student = studentService.getById(student_course.getSid());
//                Info.add(student.getSid().toString());
//                Info.add(student.getSname());
//            }
//        }
//        response.put("success", true);
//        response.put("Info", Info);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/showYourStudent")
    @ApiOperation(value = "回传老师所授学生信息及成绩")
    public ResponseEntity<Map<String, Object>> showYourStudent(@RequestParam("tname") String tname,
                                                               @RequestParam("cname") String cname) {
        Map<String,Object> response = new HashMap<>();
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("tname", tname);
        Teacher teacher = teacherService.getOne(teacherQueryWrapper);
        int tid = teacher.getTid();
        QueryWrapper<Student_Course> student_courseQueryWrapper = new QueryWrapper<>();
        student_courseQueryWrapper.eq("tid", tid);
        List<Student_Course> student_courseList = student_courseService.list(student_courseQueryWrapper);
        QueryWrapper<Classroom> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("cname", cname);
        Classroom course = classroomService.getOne(courseQueryWrapper);
        List<List<Object>> Info = new ArrayList<>();
        for (Student_Course student_course : student_courseList) {
            if (Objects.equals(student_course.getCid(), course.getCid())) {
                Student student = studentService.getById(student_course.getSid());
                // 查询成绩信息
                QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<>();
                gradeQueryWrapper.eq("sid", student.getSid());
                gradeQueryWrapper.eq("cid", course.getCid());
                Grade grade = gradeService.getOne(gradeQueryWrapper);
                List<Object> studentInfoWithGrade = new ArrayList<>();
                studentInfoWithGrade.add(student.getSid().toString());
                studentInfoWithGrade.add(student.getSname());
                if (grade != null) {
                    studentInfoWithGrade.add(grade.getRegular());
                    studentInfoWithGrade.add(grade.getMidterm());
                    studentInfoWithGrade.add(grade.getLab());
                    studentInfoWithGrade.add(grade.getEnd());
                    studentInfoWithGrade.add(grade.getTotal());
                } else {
                    // 如果没有成绩信息，可以添加null或者默认值
                    studentInfoWithGrade.add(null);
                    studentInfoWithGrade.add(null);
                    studentInfoWithGrade.add(null);
                    studentInfoWithGrade.add(null);
                    studentInfoWithGrade.add(null);
                }
                Info.add(studentInfoWithGrade);
            }
        }
        response.put("success", true);
        response.put("Info", Info);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateGrade")
    @ApiOperation(value = "更新学生成绩")
    public ResponseEntity<Map<String, Object>> updateGrade(@RequestParam("sid") Integer sid,
                                                           @RequestParam("type") String type,
                                                           @RequestParam("value") Integer value) {
        Map<String,Object> response = new HashMap<>();
        Grade grade = gradeService.getOne(new QueryWrapper<Grade>().eq("sid", sid));
        if (grade==null)
        {
            Grade grade1 = null;
            switch (type) {
                case "regular":
                    grade.setRegular(value);
                    break;
                case "midterm":
                    grade.setMidterm(value);
                    break;
                case "lab":
                    grade.setLab(value);
                    break;
                case "end":
                    grade.setEnd(value);
                    break;
            }
            gradeService.updateById(grade);
            // 重新计算总成绩
            calculateTotal(grade);
            response.put("success", true);
        }
        if (grade != null) {
            switch (type) {
                case "regular":
                    grade.setRegular(value);
                    break;
                case "midterm":
                    grade.setMidterm(value);
                    break;
                case "lab":
                    grade.setLab(value);
                    break;
                case "end":
                    grade.setEnd(value);
                    break;
            }
            gradeService.updateById(grade);
            // 重新计算总成绩
            calculateTotal(grade);
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateGrades")
    public ResponseEntity<Map<String, Object>> updateGrades(@RequestBody String jsonPayload) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Received JSON payload: " + jsonPayload);
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> updates = mapper.readValue(jsonPayload, List.class);

            if (updates == null || updates.isEmpty()) {
                System.out.println("Updates list is empty");
                response.put("success", false);
                response.put("message", "没有需要更新的数据");
                return ResponseEntity.ok(response);
            }

            for (Map<String, Object> update : updates) {
                // 直接将 sid 转换为整数
                System.out.println("check0");
                Integer sid = Integer.parseInt((String) update.get("sid"));
                System.out.println("check1");
                String type = (String) update.get("type");
                System.out.println("check2");
                Object valueObj = update.get("value");
                System.out.println("check3");
                Object tname = update.get("tname");
                Object cname = update.get("cname");
                int tid = teacherService.getOne(new QueryWrapper<Teacher>().eq("tname",tname)).getTid();
                System.out.println("tid:"+tid);
                int cid = classroomService.getOne(new QueryWrapper<Classroom>().eq("cname",cname)).getCid();
                System.out.println("cid"+cid);
                if (valueObj == null) {
                    System.out.println("Value is null for sid: " + sid);
                    continue; // 跳过这个更新
                }

                // 尝试将 value 转换为整数
                System.out.println("check4");
                Integer value = null;
                System.out.println("check5");
                try {
                    System.out.println("value");
                    value = (Integer) valueObj; // 直接将 valueObj 转换为 Integer
                    System.out.println("valuegood");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid integer format for sid: " + sid + ", value: " + valueObj);
                    continue; // 跳过这个更新
                }
                System.out.println("gradeservice");
                Grade grade = gradeService.getRelation(sid,cid,tid);
                System.out.println("gradeservicegood");
                if (grade == null) {
                    System.out.println("GRADE is null for sid: " + sid);
                    response.put("success", false);
                    response.put("message", "学生ID为" + sid + "的成绩不存在");
                    return ResponseEntity.ok(response);
                } else {
                    switch (type) {
                        case "regular":
                            grade.setRegular(value);
                            break;
                        case "midterm":
                            grade.setMidterm(value);
                            break;
                        case "lab":
                            grade.setLab(value);
                            break;
                        case "end":
                            grade.setEnd(value);
                            break;
                    }
                    calculateTotal(grade);
                }
            }
            response.put("success", true);
            response.put("message", "所有成绩更新成功");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新成绩时发生错误: " + e.getMessage());
            System.out.println("更新成绩时发生错误: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    public void calculateTotal(Grade grade) {
        double total = 0.1 * grade.getRegular() + 0.2 * grade.getMidterm() + 0.15 * grade.getLab() + 0.55 * grade.getEnd();
        grade.setTotal((int) total);
        System.out.println(grade);
        gradeService.updateById(grade);
    }

    @PostMapping("allocateGrade")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> allocateGrades(@RequestParam("regular")Integer regular,
                                                             @RequestParam("midterm") Integer midterm,
                                                             @RequestParam("lab") Integer lab,
                                                             @RequestParam("end") Integer end,
                                                             @RequestParam("cname") String cname,
                                                             @RequestParam("tname") String tname)
    {
        Map<String,Object>response = new HashMap<>();
        int cid = classroomService.getOne(new QueryWrapper<Classroom>().eq("cname",cname)).getCid();
        int tid = teacherService.getOne(new QueryWrapper<Teacher>().eq("tname",tname)).getTid();
        List<Grade>grades = gradeService.getList(cid,tid);
        for (Grade grade:grades)
        {
            grade.setRegular(regular);
            grade.setMidterm(midterm);
            grade.setLab(lab);
            grade.setEnd(end);
            calculateTotal(grade);
            gradeService.updateById(grade);
        }
        response.put("success",true);
        return ResponseEntity.ok(response);
    }
}
