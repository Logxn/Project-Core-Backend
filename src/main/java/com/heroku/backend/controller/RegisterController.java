package com.heroku.backend.controller;

import com.heroku.backend.data.RegisterData;
import com.heroku.backend.data.response.RegisterResponseData;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserExistsException;
import com.heroku.backend.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService){
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseData> Register(RegisterData registerData) throws MissingParameterException, UserExistsException {
        return registerService.register(registerData);
    }
}
