package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.DormitoryService;
import cn.edu.cqu.nsers.Service.StudentService;
import cn.edu.cqu.nsers.pojo.Dormitory;
import cn.edu.cqu.nsers.pojo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/functionPage")
@Api(value = "功能-宿舍")
public class Func_Dor {
    @Autowired
    public DormitoryService dormitoryService;
    @Autowired
    public StudentService studentService;


    @PostMapping("/getRoommate")        //根据当前学生ID返回他的宿舍信息和他的舍友
    @ApiOperation(value = "回传舍友与宿舍信息")
    public ResponseEntity<Map<String, Object>> GetRoommate(@RequestParam("sid") Integer sid)
    {
        Map<String, Object> response = new HashMap<>();

        //找到宿舍信息
        Student student = studentService.getById(sid);
        Dormitory dormitory = dormitoryService.getById(student.getDormitoryId());
        Map<String, Object> columnMap = new HashMap<>();

        //找到学生信息
        columnMap.put("dormitory_id", dormitory.getDormitoryId());
        Collection<Student> roommate = studentService.listByMap(columnMap);

        response.put("dormitory", dormitory);
        response.put("roommate", roommate);
        response.put("success",true);
        return ResponseEntity.ok(response);
    }
}
