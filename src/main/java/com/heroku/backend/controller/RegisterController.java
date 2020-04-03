package com.heroku.backend.controller;

import com.heroku.backend.service.RegisterService;
import jdk.vm.ci.code.Register;
import org.springframework.web.bind.annotation.GetMapping;

public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService){
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public void Register()
    {

    }
}
