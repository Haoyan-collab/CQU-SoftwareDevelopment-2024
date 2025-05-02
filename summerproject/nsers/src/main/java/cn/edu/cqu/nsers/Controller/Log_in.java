package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.*;
import cn.edu.cqu.nsers.pojo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/start")
@Api(value = "学生登录注册界面")
public class Log_in {
    @Autowired
    public StudentService studentService;
    @Autowired
    public SendEmailService mailService;
    @Autowired
    public MajorService majorService;
    @Autowired
    public DepartmentService departmentService;
    @Autowired
    public DormitoryService dormitoryService;
    @Autowired
    public SchoolemailService schoolemailService;
    @Autowired
    public TeacherService teacherService;
    @Autowired
    public KshService kshService;

    @GetMapping("/loginPage")
    @ApiOperation(value = "展示登录界面")
    public String showLoginPage() {
        return "loginPage";
    }

    @PostMapping("/teacherLogin")       //对比传入的老师信息，若学号错误或密码不正确则返回false
    @ApiOperation(value = "教师登录界面")
    public ResponseEntity<Map<String, Object>> teacherLogin(@RequestParam("tnumber") Integer tnumber, @RequestParam("tpassword") String tpassword) {
        Map<String, Object> response = new HashMap<>();
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();

        //找到老师信息
        teacherQueryWrapper.eq("tnumber", tnumber);
        Teacher teacher = teacherService.getOne(teacherQueryWrapper);

        //判断能否成功登陆
        if (teacher == null || !teacher.getTpassword().equals(tpassword)) {
            response.put("success", false);
            response.put("message", "账号或密码错误");
            return ResponseEntity.status(401).body(response);
        }

        response.put("tid", teacher.getTid());
        response.put("tname", teacher.getTname());
        response.put("success", true);
        response.put("message", "成功登陆");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/studentLogin")      //对比传入的学生信息，若学号错误或密码不正确则返回false
    @ApiOperation(value = "学生登录界面")
    public ResponseEntity<Map<String, Object>> studentLogin(@RequestParam("sid") Integer sid, @RequestParam("spassword") String spassword) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentService.getById(sid);

        //判断能否登陆
        if (student == null || !student.getSpassword().equals(spassword)) {
            response.put("success", false);
            response.put("message", "账号或密码错误");
            return ResponseEntity.status(401).body(response);
        }

        Schoolemail schoolemail = schoolemailService.getById(student.getEid());
        response.put("semail", student.getSemail());
        response.put("ebool", schoolemail.isEbool());
        response.put("update_photo",student.getUpdatePhoto());
        response.put("spicture", student.getSpicture());
        response.put("success", true);
        response.put("message", "成功登陆");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/registerPage")
    @ApiOperation(value = "展示注册界面")
    public String showRegisterPage() {
        return "registerPage";
    }

    @PostMapping("/judgeRegister")      //判断能不能注册
    @ApiOperation(value = "判断注册回传信息")
    public ResponseEntity<Map<String, Object>> judgeRegister(@RequestParam("sid") Integer sid)
    {
        Student student = studentService.getById(sid);
        Map<String,Object>response = new HashMap<>();
        if(student == null)
        {
            response.put("success", false);
            response.put("message", "该学号不存在！请重新输入");
            return ResponseEntity.status(401).body(response);
        }
        else if(student.getRegisterAccount() == 1)
        {
            response.put("success", false);
            response.put("message", "该账号已被注册！请重新输入");
            System.out.println("rs==1!!!!!!");
            return ResponseEntity.status(401).body(response);
        }
        else
        {
            response.put("success", true);
            response.put("message", "账号可以被注册！");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register")       //进行注册操作
    @ApiOperation(value = "进行注册操作")
    public ResponseEntity<Map<String, Object>> judgeId(@RequestParam(value = "sid") Integer sid,
                                                       @RequestParam(value = "spassword1",required = false) String spassword1,
                                                       @RequestParam(value = "spassword2",required = false) String spassword2,
                                                       @RequestParam(value = "sphone"    ,required = false) String sphone,
                                                       @RequestParam(value = "semail"    ,required = false) String semail) {
        Map<String, Object> response = new HashMap<>();

        //找到该学生对应的专业和宿舍
        Student student = studentService.getById(sid);
        Dormitory dormitory = dormitoryService.getById(student.getDormitoryId());
        Major major = majorService.getById(student.getMid());

        //学院报到人数加一
        Department department = departmentService.getById(major.getDepartmentId());
        department.setDepartmentCnt(department.getDepartmentCnt() + 1);
        departmentService.updateById(department);

        //宿舍入住人数加一
        dormitory.setDormitoryCnt(dormitory.getDormitoryCnt() + 1);
        dormitoryService.updateById(dormitory);

        //可视化大屏数据更新
        Ksh newKsh = new Ksh();
        newKsh.setCount(1);
        newKsh.setDorm_name(dormitory.getDormitoryGarden());
        newKsh.setSgender(student.getSgender());
        newKsh.setSmajor(major.getMname());
        newKsh.setSprovince(student.getSprovince());
        newKsh.setSid(student.getSid());
        newKsh.setSname(student.getSname());
        kshService.saveOrUpdate(newKsh);

        //完成注册
        student.setSid(sid);
        student.setSpassword(spassword1);
        student.setSphone(sphone);
        student.setSemail(semail);
        student.setRegisterAccount((byte) 1);
        studentService.updateById(student);

        response.put("success", true);
        response.put("message", "注册成功！");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/emailVerificationPage")
    @ApiOperation(value = "展示邮箱验证界面")
    public String showEmailVerificationPage() {
        return "emailVerificationPage";
    }

    @PostMapping("/emailVerification")      //发送验证码
    @ApiOperation(value = "调用发验证码功能")
    public ResponseEntity<Map<String, Object>> emailVerification(@RequestParam Integer sid) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentService.getById(sid);

        //判断能否成功发送验证码
        if(student==null)
        {
            response.put("success", false);
            response.put("message", "学号不存在！请重新输入");
            return ResponseEntity.status(401).body(response);
        }

        //发送验证码
        String semail = student.getSemail();
        String code = mailService.generateVerificationCode();
        mailService.sendVerificationCode(semail, code);

        response.put("semail", semail);
        response.put("success", true);
        response.put("message", "验证码发送成功");
        response.put("verification", code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/setPasswordPage")
    @ApiOperation(value = "展示更改密码页面")
    public String showSetPasswordPage() {
        return "setPasswordPage";
    }

    @PostMapping("/setPassword")        //设置密码
    @ApiOperation(value = "设置密码操作")
    public ResponseEntity<Map<String, Object>> setNewPassword(@RequestParam("sid") String sid, //定位学生
                                                              @RequestParam("spassword1") String spassword1,
                                                              @RequestParam("spassword2") String spassword2)
    {
        Map<String, Object> response = new HashMap<>();

        //更新密码
        Student student = studentService.getById(sid);
        student.setSpassword(spassword1);
        studentService.updateById(student);

        response.put("success", true);
        response.put("message", "密码修改成功!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/authenticationPage")
    @ApiOperation(value = "展示二次验证页面")
    public String showAuthenticationPage() {
        return "authenticationPage";
    }

    @PostMapping("/authentication")     //邮箱二次验证
    @ApiOperation(value = "二次验证")
    public ResponseEntity<Map<String, Object>> Authentication(@RequestParam String semail) {
        Map<String, Object> response = new HashMap<>();

        //发送验证码
        String code = mailService.generateVerificationCode();
        mailService.sendVerificationCode(semail, code);

        response.put("success", true);
        response.put("message", "验证码发送成功");
        response.put("verification", code);
        System.out.println(code+"!!!!!");
        return ResponseEntity.ok(response);
    }
}
