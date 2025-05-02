package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.DepartmentService;
import cn.edu.cqu.nsers.Service.DormitoryService;
import cn.edu.cqu.nsers.pojo.Dormitory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
@Api(value = "资讯界面")
public class News {
    @Autowired
    public DepartmentService departmentService;
    @Autowired
    public DormitoryService dormitoryService;

    @GetMapping("/newsPage")
    @ApiOperation(value = "展示资讯界面")
    public String showNewsPage() {
        return "newsPage";
    }

    @PostMapping("/getInfo")
    @ApiOperation(value = "回传宿舍入住和学院报道信息")
    public ResponseEntity<Map<String,Object>> getInfo()
    {
        Map<String, Object> response = new HashMap<>();

        //获取柱状图和饼图数据
        int cnt1 = departmentService.getById(1).getDepartmentCnt();
        int cnt2 = departmentService.getById(2).getDepartmentCnt();
        int cnt3 = departmentService.getById(3).getDepartmentCnt();
        long total1 = dormitoryService.getSumOfGarden("梅园");
        long total2 = dormitoryService.getSumOfGarden("竹园");
        long total3 = dormitoryService.getSumOfGarden("松园");
        long total4 = dormitoryService.getSumOfGarden("兰园");

        response.put("success",true);
        response.put("Computer",cnt1);
        response.put("Mathematics",cnt2);
        response.put("Philosophy",cnt3);
        response.put("Plumgarden",total1);
        response.put("Bamboogarden",total2);
        response.put("Pinegarden",total3);
        response.put("Orchidgarden",total4);
        return ResponseEntity.ok(response);
    }

}
