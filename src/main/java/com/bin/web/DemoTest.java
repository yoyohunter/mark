package com.bin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangbin on 16/10/24.
 */

@Controller
@RequestMapping("/demo")
public class DemoTest {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
