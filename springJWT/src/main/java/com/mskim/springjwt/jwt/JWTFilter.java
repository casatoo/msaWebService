package com.mskim.springjwt.jwt;

import com.mskim.springjwt.dto.CustomUserDetails;
import com.mskim.springjwt.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AUTH_TOKEN".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    if (jwtUtil.isExpired(token)) {
                        filterChain.doFilter(request, response);
                        return;
                    }

                    String username = jwtUtil.getUsername(token);
                    String role = jwtUtil.getRole(token);

                    // UserDetails 객체 생성
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword("temppassword"); // 임시 비밀번호 (보안)
                    user.setRole(role);

                    CustomUserDetails customUserDetails = new CustomUserDetails(user);

                    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    break; // Cookie에서 JWT를 찾았으면 반복문 종료
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
