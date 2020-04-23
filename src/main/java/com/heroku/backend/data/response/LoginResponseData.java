package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponseData {

    private LocalDateTime localDateTime;
    private String username;
    private Status status;
    private String accessToken;

    public LoginResponseData(String username, String accessToken, LocalDateTime localDateTime){
        this.username = username;
        this.accessToken = accessToken;
        this.localDateTime = localDateTime;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
