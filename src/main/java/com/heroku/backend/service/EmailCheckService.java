package com.heroku.backend.service;

import com.heroku.backend.data.response.EmailResponseData;
import com.heroku.backend.entity.EmailEntity;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.MissingParameterException;
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

    public ResponseEntity<EmailResponseData> checkEmail(String email) throws MissingParameterException {
        if(email == null || email.isEmpty())
            throw new MissingParameterException();

        EmailEntity userFoundByEmail = emailRepository.findByEmail(email);

        EmailResponseData responseData = new EmailResponseData(email, LocalDateTime.now());
        responseData.setStatus(Status.LOGIN);

        if (userFoundByEmail == null) {
            responseData.setStatus(Status.REGISTER);
        }

        return ResponseEntity.ok(responseData);
    }
}
