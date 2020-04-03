package com.heroku.backend.controller;

import com.heroku.backend.data.LoginData;
import com.heroku.backend.data.response.LoginResponseData;
import com.heroku.backend.exceptions.LoggedInException;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.InvalidUserPassException;
import com.heroku.backend.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){ this.loginService = loginService; }

    @PostMapping("/login")
    private ResponseEntity<LoginResponseData> login(@RequestBody LoginData loginData)
            throws MissingParameterException, InvalidUserPassException, LoggedInException {
        return loginService.login(loginData);
    }


}
