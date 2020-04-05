package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshTokenResponse {
    private Status status;
    private String accessToken;
    private LocalDateTime timestamp;

    public RefreshTokenResponse(Status status, String accessToken, LocalDateTime localDateTime){
        this.status = status;
        this.accessToken = accessToken;
        this.timestamp = localDateTime;
    }
}
