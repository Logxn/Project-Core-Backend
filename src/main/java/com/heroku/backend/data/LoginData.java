package com.heroku.backend.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginData {
    private String email;
    private String password;
}
