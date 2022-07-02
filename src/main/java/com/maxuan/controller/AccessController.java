package com.maxuan.controller;

import com.maxuan.service.AccessLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/******************************
 *
 * 码炫课堂技术交流Q群：963060292
 * 主讲：smart哥
 *
 ******************************/
@RestController
@RequestMapping("access")
public class AccessController {

    @ResponseBody
    @GetMapping("accessLimit")
    @AccessLimit(seconds = 60,maxCount = 10)
    public String accessLimit(){
        return "it is ok!";
    }
}
