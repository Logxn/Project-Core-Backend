package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogoutResponse {
    private Status status;
    private LocalDateTime localDateTime;

    public LogoutResponse(Status status, LocalDateTime localDateTime){
        this.status = status;
        this.localDateTime = localDateTime;
    }
}
