package com.heroku.backend.data;

import java.time.LocalDateTime;

public class PingResponseData {
    private String status;
    private LocalDateTime localDateTime;

    public PingResponseData(String status, LocalDateTime localDateTime) {
        this.status = status;
        this.localDateTime = localDateTime;
    }

    public String getStatus()
    {
        return this.status;
    }

    public LocalDateTime getLocalDateTime()
    {
        return this.localDateTime;
    }

}
