package cn.edu.cqu.nsers.Controller;

import cn.edu.cqu.nsers.Service.KshService;
import cn.edu.cqu.nsers.pojo.Ksh;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "可视化大屏")
public class KshController{
    @Autowired
    private KshService kshService;

    @PostMapping("/Ksh")
    @ApiOperation(value = "回传可视化大屏所需信息")
    public List<Ksh> getAllInfo() {
        return kshService.getAllInfo();
    }
}
