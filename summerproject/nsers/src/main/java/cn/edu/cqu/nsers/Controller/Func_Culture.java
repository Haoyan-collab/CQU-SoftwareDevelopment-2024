package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.FacilityService;
import cn.edu.cqu.nsers.Service.TeacherService;
import cn.edu.cqu.nsers.pojo.Facility;
import cn.edu.cqu.nsers.pojo.Teacher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/functionPage")
@Api(value = "功能-文化")
public class Func_Culture {
    @Autowired
    public TeacherService teacherService;
    @Autowired
    public FacilityService facilityService;

    @GetMapping("/teacherPage")
    @ApiOperation(value = "展示老师页面")
    public String showTeacherPage() {
        return "teacherPage";
    }

    @PostMapping("/showTeacher")     //在师资页面一开始展示所有老师的所有信息
    @ApiOperation(value = "回传老师列表")
    public ResponseEntity<Map<String, Object>> showTeacher()
    {
        Map<String, Object> response = new HashMap<>();
        List<Teacher> teacherList= teacherService.list();
        response.put("teacherList", teacherList);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/searchTeacher")     //实现搜索功能，输入老师名字返回老师信息
//    public ResponseEntity<Map<String, Object>> searchTeacher(@RequestParam("tname") String tname) {
//        Map<String, Object> response = new HashMap<>();
//        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
//
//        //找到老师信息
//        teacherQueryWrapper.eq("tname", tname);
//        List<Teacher> teacher = teacherService.list(teacherQueryWrapper);
//
//        response.put("success", true);
//        response.put("teacher", teacher);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/facilityPage")
    @ApiOperation(value = "展示设施页面")
    public String showFacilityPage() {
        return "facilityPage";
    }

    @PostMapping("/showFacility")      //在设施页面一开始展示所有设施的所有信息
    @ApiOperation(value = "回传页面信息")
    public ResponseEntity<Map<String, Object>> showFacility()
    {
        Map<String, Object> response = new HashMap<>();
        List<Facility> facilityList= facilityService.list();
        response.put("facilityList", facilityList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/searchFacility")     //实现搜索功能，输入设施类型返回设施信息
    @ApiOperation(value = "回传搜索到的页面信息")
    public ResponseEntity<Map<String, Object>> searchFacility(@RequestParam("fkind") String fkind)
    {
        Map<String, Object> response = new HashMap<>();
        QueryWrapper<Facility> facilityQueryWrapper = new QueryWrapper<>();

        //找到设施信息
        facilityQueryWrapper.eq("fkind", fkind);
        List<Facility> facilityList = facilityService.list(facilityQueryWrapper);

        response.put("facilityList", facilityList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calendPage")
    @ApiOperation(value = "展示日历页面")
    public String showCalendPage() {
        return "calendPage";
    }

    @GetMapping("/mattersPage")
    @ApiOperation(value = "展示事务页面")
    public String showMattersPage() {
        return "mattersPage";
    }
}
