package com.mskim.springjwt.service;

import com.mskim.springjwt.dto.LoginDTO;
import com.mskim.springjwt.entity.User;
import com.mskim.springjwt.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserRepository userRepository;

    public User loginProcess(LoginDTO loginDTO) {

        return userRepository.findById(1).get();
    }
}
