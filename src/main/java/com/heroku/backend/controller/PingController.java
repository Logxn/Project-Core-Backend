package com.heroku.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;
import com.heroku.backend.service.PingService;

import java.util.Map;

@RestController
public class PingController
{
    private final PingService pingService;

    public PingController(PingService pingService)
    {
        this.pingService = pingService;
    }

    @RequestMapping(path="/ping")
    public ResponseEntity<Map<String, String>> ping()
    {
        return pingService.ping();
    }
}