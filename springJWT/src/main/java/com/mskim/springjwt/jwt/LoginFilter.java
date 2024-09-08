package com.mskim.springjwt.jwt;

import com.mskim.springjwt.dto.CustomUserDetails;
import com.mskim.springjwt.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/loginProc"); // 로그인 요청 URL
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // 검증을 위해 토큰에 담는다
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return  authenticationManager.authenticate(authToken);
    }

    // 로그인 성공 시 실행 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)  throws IOException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> aurhorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = aurhorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*36000L);
        response.addHeader("Authorization", "Bearer " + token);

        // 쿠키인증 방식을 위해 생성
        Cookie cookie = new Cookie("AUTH_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // 개발 환경에서는 false로 설정
        cookie.setDomain("desktop-kjdmk88"); // 도메인 설정
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        // 메인화면으로 이동
        response.sendRedirect("/main");
    }

    // 로그인 실패 시 실행 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);

    }

}
