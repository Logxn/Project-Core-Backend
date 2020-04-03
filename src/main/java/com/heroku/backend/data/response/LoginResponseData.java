package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponseData {

    private LocalDateTime localDateTime;
    private String username;
    private Status status;

    public LoginResponseData(String username, LocalDateTime localDateTime){
        this.username = username;
        this.localDateTime = localDateTime;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
