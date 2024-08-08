package com.ks.EventManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/")
    public String returnPage(){
        return "page";
    }

    @RequestMapping("/hello")
    public String returnHelloPage(){
        return "Hello";
    }
}
