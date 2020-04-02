package com.heroku.backend.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PingService {

    public ResponseEntity<Map<String, String>> ping()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();

        Map<String, String> response = new LinkedHashMap<>();
        response.put("status", "alive");
        response.put("timestamp", dateTimeFormatter.format(localDateTime));

        return ResponseEntity.ok(response);
    }
}
