package com.heroku.backend.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginData {
    private String email;
    private String password;

    public LoginData(String email, String password){
        this.email = email;
        this.password = password;
    }
}
