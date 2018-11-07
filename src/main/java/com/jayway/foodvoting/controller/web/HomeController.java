package com.jayway.foodvoting.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/stats"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "api.documentation")
    public String test() {

        return "html5/api.guide.html";
    }

}