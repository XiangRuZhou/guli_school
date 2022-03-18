package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.ResultMsg;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin //解决跨域
public class EduLoginController {

    @PostMapping("/login")
    public ResultMsg login(){
        return ResultMsg.ok().data("token","admin");
    }

    @GetMapping("/info")
    public ResultMsg info(){
        return ResultMsg.ok().data("roles","admin").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
