package com.mskim.springjwt.service;

import com.mskim.springjwt.dto.CustomUserDetails;
import com.mskim.springjwt.entity.User;
import com.mskim.springjwt.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // 생성자 주입 방식을 써야 결합도가 낮아진다
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username);

        if(!ObjectUtils.isEmpty(userData)) {

            return new CustomUserDetails(userData);
        }

        return null;
    }
}
