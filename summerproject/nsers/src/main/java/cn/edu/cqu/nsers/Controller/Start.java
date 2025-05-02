package cn.edu.cqu.nsers.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/startPage")
@Api(value = "初页面")
public class Start {
    @GetMapping("")
    @ApiOperation(value = "展示初页面")
    public String showStartPage() {
        return "startPage";
    }
}
