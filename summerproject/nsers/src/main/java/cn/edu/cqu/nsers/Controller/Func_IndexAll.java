package cn.edu.cqu.nsers.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/functionPage")
@Api(value = "功能-最初界面")
public class Func_IndexAll {
    @GetMapping("")
    @ApiOperation(value = "展示最初主页")
    public String showFunctIndexAll() {
        return "functionPage";
    }
}
