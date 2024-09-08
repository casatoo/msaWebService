package com.mskim.springjwt.service;

import com.mskim.springjwt.dto.LoginDTO;
import com.mskim.springjwt.entity.User;

public interface LoginService {

    User loginProcess(LoginDTO loginDTO);
}
