package cn.edu.cqu.nsers.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schoolBus")
@Api(value = "其他资讯")
public class SchoolBus {
    @GetMapping("busPage")
    @ApiOperation(value = "展示校车表")
    public String showBusPage() {
        return "busPage";
    }

    @GetMapping("busMainPage")
    @ApiOperation(value = "展示校车主功能")
    public String showBusMainPage() {
        return "busMainPage";
    }

    @GetMapping("mapPage")
    @ApiOperation(value = "展示地图功能")
    public String showMapPage() {
        return "mapPage";
    }

    @GetMapping("yellowPage")
    @ApiOperation(value = "展示黄页功能")
    public String showYellowPage() {
        return "yellowPage";
    }
}
