package com.heroku.backend.data.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PingResponseData {
    private String status;
    private LocalDateTime localDateTime;

    public PingResponseData(String status, LocalDateTime localDateTime) {
        this.status = status;
        this.localDateTime = localDateTime;
    }
}
