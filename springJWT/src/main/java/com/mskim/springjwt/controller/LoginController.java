package com.mskim.springjwt.controller;

import com.mskim.springjwt.dto.LoginDTO;
import com.mskim.springjwt.entity.User;
import com.mskim.springjwt.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    private final LoginService loginService;

    // 생성자 주입 방식
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginView";
    }

}
