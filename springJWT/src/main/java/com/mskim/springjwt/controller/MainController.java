package com.mskim.springjwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String view (HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
        return "domain/main/mainView"; // "mainView" 뷰를 반환
    }

    @GetMapping("/bagit")
    public String bagitView (HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
        return "domain/bagit/bagit";
    }

}
