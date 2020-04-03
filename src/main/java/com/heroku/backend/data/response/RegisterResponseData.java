package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponseData {

    private LocalDateTime localDateTime;
    private Status status;

    public RegisterResponseData(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
