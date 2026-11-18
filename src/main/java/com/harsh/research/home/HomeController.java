package com.harsh.research.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String rootHandler(){
        return "Welcome to ai research";
    }
}
