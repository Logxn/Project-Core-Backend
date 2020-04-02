package com.heroku.backend.service;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PingService {
    public ResponseEntity<String> ping()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();

        String response = "Alive - " + dateTimeFormatter.format(localDateTime);

        return ResponseEntity.ok(response);
    }
}
