package com.heroku.backend.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmailCheckService
{
    public ResponseEntity<Map<String, Object>> checkEmail(String email)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, Object> response = new LinkedHashMap<>();

        /*
            Basic Idea:
            When registering a company/user account - the frontend will ask for an email first
            The frontend will then send a request to /user/checkEmail to see the current Status of this email.

            If it exists in the database: it should come back with something like this
            {"email":email, "status":"login", "timestamp":currentTimestamp}

            If its not in the database: it should come back with something like this
            {"email":email, "status":"register", "timestamp":currentTimestamp}

            Either way - we will send back an OK response to the user.
            No Authentication required.
         */

        response.put("timestamp", localDateTime);
        return ResponseEntity.ok(response);
    }
}
