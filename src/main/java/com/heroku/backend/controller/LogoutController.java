package com.heroku.backend.controller;

import com.heroku.backend.data.LogoutData;
import com.heroku.backend.data.response.LogoutResponseData;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserNotLoggedInException;
import com.heroku.backend.service.LogoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    private final LogoutService logoutService;

    public LogoutController(LogoutService logoutService){
        this.logoutService = logoutService;
    }

    @PostMapping("/user/logout")
    public ResponseEntity<LogoutResponseData> logout(@RequestBody LogoutData logoutData) throws MissingParameterException, UserNotLoggedInException {
        return logoutService.logout(logoutData);
    }
}
