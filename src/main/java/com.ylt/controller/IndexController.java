package com.ylt.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/message")
    public String printMessage(ModelMap map) {
        map.addAttribute("message", "this is printMessage Info");
        return "message";
    }

    @RequestMapping(value = "/staticPage",method = RequestMethod.GET)
    public String redirect(){
        return "redirect:/html/final.html";
    }

}



