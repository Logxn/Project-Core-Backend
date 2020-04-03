package com.heroku.backend.service;

import com.heroku.backend.data.ResponseData;
import com.heroku.backend.entity.EmailEntity;
import com.heroku.backend.enums.Status;
import com.heroku.backend.repository.EmailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailCheckService {

    public EmailRepository emailRepository;

    public EmailCheckService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public ResponseEntity<ResponseData> checkEmail(String email) {
        EmailEntity userFoundByEmail = emailRepository.findByEmail(email);

        ResponseData responseData = new ResponseData(email, LocalDateTime.now());
        responseData.setStatus(Status.LOGIN);

        if (userFoundByEmail == null) {
            responseData.setStatus(Status.REGISTER);
        }

        return ResponseEntity.ok(responseData);
    }
}
