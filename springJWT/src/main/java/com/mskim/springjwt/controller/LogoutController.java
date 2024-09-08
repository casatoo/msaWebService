package com.mskim.springjwt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // JWT 쿠키 삭제
        Cookie jwtCookie = new Cookie("AUTH_TOKEN", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // HTTPS 환경에서만 작동
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // 쿠키 삭제를 위해 Max-Age를 0으로 설정

        response.addCookie(jwtCookie);

        return "redirect:/login?logout"; // 로그아웃 후 리다이렉트 URL
    }
}
