package com.heroku.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.heroku.backend.service.PingService;

@RestController
public class PingController
{
    private final PingService pingService;

    public PingController(PingService pingService)
    {
        this.pingService = pingService;
    }

    @RequestMapping(path="/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping()
    {
        return pingService.ping();
    }
}
