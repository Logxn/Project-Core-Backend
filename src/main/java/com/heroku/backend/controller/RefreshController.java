package com.heroku.backend.controller;

import com.heroku.backend.data.RefreshTokenData;
import com.heroku.backend.data.response.RefreshTokenResponse;
import com.heroku.backend.exceptions.ExpiredTokenException;
import com.heroku.backend.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshController {
    private final RefreshTokenService refreshService;

    public RefreshController(RefreshTokenService refreshService){
        this.refreshService = refreshService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenData refreshTokenData) throws ExpiredTokenException {
        return refreshService.refreshToken(refreshTokenData);
    }
}
