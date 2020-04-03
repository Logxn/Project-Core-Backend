package com.heroku.backend.controller;

import com.heroku.backend.data.response.RegisterResponseData;
import com.heroku.backend.enums.AccountType;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserExistsException;
import com.heroku.backend.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService){
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public ResponseEntity<RegisterResponseData> Register(@RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password,
                                                         @RequestParam("account_type") AccountType accountType) throws MissingParameterException, UserExistsException {
        return registerService.register(email, username, password, accountType);
    }
}
