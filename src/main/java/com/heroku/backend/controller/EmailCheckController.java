package com.heroku.backend.controller;

import com.heroku.backend.data.ResponseData;
import com.heroku.backend.service.EmailCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailCheckController {

    private final EmailCheckService emailCheckService;

    public EmailCheckController(EmailCheckService emailCheckService) {
        this.emailCheckService = emailCheckService;
    }

    @GetMapping("/user/checkEmail")
    public ResponseEntity<ResponseData> checkEmail(@RequestParam("email") String email) {
        return emailCheckService.checkEmail(email);
    }
}
