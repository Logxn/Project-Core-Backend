package com.heroku.backend.data;

import lombok.Data;

@Data
public class LogoutData {
    private String username;

    public LogoutData(String username){
        this.username = username;
    }
}
