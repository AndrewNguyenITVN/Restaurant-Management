package com.example.demo.controller;

import com.example.demo.payload.ResponseData;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.service.LoginService;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, String password) {
        ResponseData responseData = new ResponseData();

//        SecretKey secretKey = Jwts.SIG.HS256.key().build();
//        String secretString = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println(secretString);

        if(loginService.checkLogin(username, password)){
            String token = jwtUtils.generateToken(username);
            responseData.setData(token);

        }else {
            responseData.setData("");
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        ResponseData responseData = new ResponseData();

        responseData.setData(loginService.addUser(signUpRequest));

        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }


}
