package com.ks.EventManagement.controller;

import com.ks.EventManagement.dto.UserDto;
import com.ks.EventManagement.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@AllArgsConstructor
public class Controller {
    private final UserService userService;

    @RequestMapping("/")
    public String returnPage(){
        return "page";
    }

    @RequestMapping("/user/login")
    public String loginPage(HttpServletRequest request,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password){
        try {
            request.login(username, password);
            return "Logged successfully";
        }catch (ServletException serverException){
            return "Something went wrong try again";
        }
    }

    @RequestMapping("/user/save")
    public int saveUser(@RequestBody UserDto dto){
        return userService.saveUser(dto); //return also status code for operation
    }

    @RequestMapping("/info")
    public String returnUserInfo(Principal principal){
        return principal.toString();
    }
}
