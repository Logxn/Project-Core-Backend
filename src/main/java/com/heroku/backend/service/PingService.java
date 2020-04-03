package com.heroku.backend.service;

import com.heroku.backend.data.response.PingResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PingService {

    public ResponseEntity<PingResponseData> ping() {
        return ResponseEntity.ok(new PingResponseData("alive", LocalDateTime.now()));
    }
}