package com.jayway.foodvoting;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/vote/**", "/stats/**"})
    public String index() {
        return "index";
    }

}