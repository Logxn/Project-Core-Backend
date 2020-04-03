package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponseData {

    private LocalDateTime localDateTime;
    private Status status;

    public LoginResponseData(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
