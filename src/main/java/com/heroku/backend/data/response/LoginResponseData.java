package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class LoginResponseData {

    private LocalDateTime timestamp;
    private String username;
    private Status status;
    private String accessToken;
    private Date expires;

    public LoginResponseData(String username, String accessToken, Date expires, LocalDateTime localDateTime){
        this.username = username;
        this.accessToken = accessToken;
        this.expires = expires;
        this.timestamp = localDateTime;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
