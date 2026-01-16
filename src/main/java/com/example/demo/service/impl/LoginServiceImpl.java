package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Users;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUser(){
        List<Users> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (Users user: listUser){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setFullName(user.getFullName());
            userDTO.setPassword(user.getPassword());

            userDTOList.add(userDTO);

        }
        return userDTOList;
    }

    @Override
    public Boolean checkLogin(String username, String password) {
        Users user = userRepository.findByUsername(username);
        return  passwordEncoder.matches(password, user.getPassword());

    }

    @Override
    public Boolean addUser(SignUpRequest signUpRequest){

        Roles role = new Roles();
        role.setId(signUpRequest.getRoleId());

        Users user = new Users();
        user.setUsername(signUpRequest.getMail());
        user.setFullName(signUpRequest.getFullname());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(role);

        try{
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
