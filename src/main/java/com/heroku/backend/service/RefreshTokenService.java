package com.heroku.backend.service;

import com.heroku.backend.data.RefreshTokenData;
import com.heroku.backend.data.response.RefreshTokenResponse;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.ExpiredTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {
    private JwtTokenService jwtTokenService;

    public RefreshTokenService(JwtTokenService jwtTokenService){
        this.jwtTokenService = jwtTokenService;
    }

    public ResponseEntity<RefreshTokenResponse>refreshToken(@RequestBody RefreshTokenData refreshTokenData) throws ExpiredTokenException {
        String accessToken = refreshTokenData.getAccessToken();
        String refreshedToken = jwtTokenService.refreshToken(accessToken);

        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse(Status.SUCCESS, refreshedToken, LocalDateTime.now());
        return ResponseEntity.ok(refreshTokenResponse);
    }
}
