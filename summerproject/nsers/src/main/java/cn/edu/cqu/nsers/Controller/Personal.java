package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.*;
import cn.edu.cqu.nsers.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("")
@Api(value = "个人信息")
public class Personal {
    @Autowired
    public StudentService studentService;
    @Autowired
    public QuestionService questionService;
    @Autowired
    public ResponseService responseService;
    @Autowired
    public DormitoryService dormitoryService;

    @Autowired
    public GradeService gradeService;

    @Autowired
    public ClassroomService classroomService;

    @GetMapping("/personalPage")
    @ApiOperation(value = "展示个人信息页面")
    public String showPersonalPage() {
        return "personalPage";
    }

    @PostMapping("/personalPage")       //获取学生信息
    @ApiOperation(value = "回传学生信息")
    public ResponseEntity<Map<String, Object>> showPersonalPage(@RequestParam Integer sid) {
        Student student = studentService.getById(sid);
        Map<String, Object> response = new HashMap<>();
        response.put("sname", student.getSname());
        response.put("spicture", student.getSpicture());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/personalInfo")       //修改信息
    @ApiOperation(value = "执行修改信息操作")
    public ResponseEntity<Map<String, Object>> setAndShowPersonalInfo(@RequestParam("sid") Integer sid,
                                                                      @RequestParam(value = "sphone",required = false) String sphone,
                                                                      @RequestParam(value = "sprovince",required = false) String sprovince,
                                                                      @RequestParam(value = "scity",required = false) String scity,
                                                                      @RequestParam(value = "semail",required = false) String semail)
    {
        Map<String, Object> response = new HashMap<>();
        Student student = studentService.getById(sid);

        //展示所有信息
        //字符串拼接
        StringBuilder dormitoryname = new StringBuilder();
        int dormitoryid = student.getDormitoryId();
        Dormitory dormitory = dormitoryService.getById(dormitoryid);
        dormitoryname.append(dormitory.getDormitoryGarden()).append(dormitory.getDormitoryNumber()).append(dormitory.getDormitoryRoom());
        //传数据
        response.put("sname", student.getSname());
        response.put("sgender", student.getSgender());
        response.put("dormitoryname", dormitoryname);
        response.put("snumber", student.getSnumber());
        response.put("sphone", student.getSphone());
        response.put("sprovince", student.getSprovince());
        response.put("scity", student.getScity());
        response.put("semail", student.getSemail());
        response.put("success1", true);
        response.put("message", "数据传输成功");

        //选择要更新的信息
        student.setSphone(sphone);
        student.setSemail(semail);
        student.setSprovince(sprovince);
        student.setScity(scity);
        studentService.updateById(student);
        response.put("success",true);
        response.put("message2","更新成功");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/showAllQuestion")        //在点击咨询后展示所有可以问的问题
    @ApiOperation(value = "回传问题")
    public ResponseEntity<Map<String, Object>> showQuestion()
    {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> question = questionService.listMaps();
        response.put("question",question);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answer")        //根据学生输入的问题号查询相应回答
    @ApiOperation(value = "回传回答")
    public ResponseEntity<Map<String, Object>> getResponse(@RequestParam("qid") Integer qid)
    {
        Map<String, Object> response = new HashMap<>();
        Question question = questionService.getById(qid);
        Response answer = responseService.getById(question.getRid());
        response.put("answer", answer);
        return ResponseEntity.ok(response);
    }

    private static final String UPLOAD_DIR = "D:/code/java/nsers/picture/";

    @PostMapping("/avatar")     //把上传的头像存到本地并更新
    @ApiOperation(value = "存储头像")
    public ResponseEntity<Map<String, Object>> upLoadAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("sid") Integer sid) {
        Map<String, Object> response = new HashMap<>();

        // 获取文件名并保存文件
        String fileName = file.getOriginalFilename();
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());
            response.put("success", true);
            response.put("message", "头像上传成功！");

            Student student = studentService.getById(sid);
            student.setSpicture(fileName);
            student.setUpdatePhoto((byte)1);
            studentService.updateById(student);

            String spicture = student.getSpicture();
            response.put("spicture", student.getSpicture());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "头像上传失败！");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/showSpicture")
    @ApiOperation(value = "回传头像")
    public ResponseEntity<Map<String, Object>> showSpicture(@RequestParam("sid") Integer sid) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentService.getById(sid);
        response.put("spicture", student.getSpicture());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/grades")
    public ResponseEntity<Map<String,Object>> getGradesByStudentId(@RequestParam("sid") Integer sid) {
        QueryWrapper<Grade> gradeQueryWrapper = new QueryWrapper<>();
        gradeQueryWrapper.eq("sid",sid);
        List<Grade>grades = gradeService.list(gradeQueryWrapper);
        List<Map<String,Object>>list=new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        for (int i=0;i<grades.size();i++)
        {
            Grade grade = grades.get(i);
            int cid = grade.getCid();
            Map<String, Object> stringObjectMap = new HashMap<>();
            Classroom classroom = classroomService.getById(cid);
            stringObjectMap.put("cname",classroom.getCname());
            stringObjectMap.put("ctime",classroom.getCtime());
            stringObjectMap.put("regular",grade.getRegular());
            stringObjectMap.put("midterm",grade.getMidterm());
            stringObjectMap.put("lab",grade.getLab());
            stringObjectMap.put("end",grade.getEnd());
            stringObjectMap.put("total",grade.getTotal());
            list.add(stringObjectMap);
            response.put("grades",list);
        }
        return ResponseEntity.ok(response);
    }
}