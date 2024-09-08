package com.mskim.springjwt.controller;

import com.mskim.springjwt.dto.JoinDTO;
import com.mskim.springjwt.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    private final JoinService joinService;

    // 생성자 주입 방식
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String joinP() {
        return "/joinView";
    }

    @PostMapping("/joinProc")
    public void joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);
    }
}
