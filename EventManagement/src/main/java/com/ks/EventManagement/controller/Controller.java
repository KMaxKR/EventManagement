package com.ks.EventManagement.controller;

import com.ks.EventManagement.dto.UserDto;
import com.ks.EventManagement.service.EmailService;
import com.ks.EventManagement.service.UserService;
import com.ks.EventManagement.utility.JWT;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@AllArgsConstructor
public class Controller {
    private final UserService userService;
    private final EmailService emailService;
    private final JWT jwt;

    @RequestMapping("/")
    public String returnPage(){
        return "page";
    }

    @RequestMapping("/user/login")
    public void loginPage(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password){
//        try {
//            request.login(username, password);
//            return "Logged successfully";
//        }catch (ServletException serverException){
//            return "Something went wrong try again";
//        }
            jwt.loginUser(username, password, response);
    }

    @RequestMapping("/user/save")
    public int saveUser(@RequestBody UserDto dto){
        return userService.saveUser(dto); //return also status for operation
    }

    @RequestMapping("/info")
    public String returnUserInfo(Principal principal){
        return principal.toString();
    }

    @RequestMapping("/search/info/{username}")
    public String searchUser(@PathVariable("username")String username){
        //some changes in user settings by root/admin such as isActive account
        return userService.loadUserByUsername(username).toString();
    }

    @RequestMapping("/user/verification/{to}")
    public String sendEmail(@PathVariable(name = "to") String to){
        // test email sender
        emailService.sendEmail(to, "Verification", "Verification Email");
        return "sent";
    }
}
