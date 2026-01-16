package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.payload.request.SignUpRequest;

import java.util.List;

public interface LoginService {
    List<UserDTO> getAllUser();
    Boolean checkLogin(String username, String password) ;
    Boolean addUser(SignUpRequest signUpRequest);
}
