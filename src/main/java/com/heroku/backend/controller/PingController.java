package com.heroku.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.heroku.backend.service.PingService;

@RestController
public class PingController
{
    private final PingService pingService;

    public PingController(PingService pingService)
    {
        this.pingService = pingService;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping()
    {
        return pingService.ping();
    }
}
