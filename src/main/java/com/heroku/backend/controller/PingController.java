package com.heroku.backend.controller;

import com.heroku.backend.data.response.PingResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heroku.backend.service.PingService;

@RestController
public class PingController
{
    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/ping")
    public ResponseEntity<PingResponseData> ping() {
        return pingService.ping();
    }
}