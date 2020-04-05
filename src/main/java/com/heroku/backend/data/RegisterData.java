package com.heroku.backend.data;

import com.heroku.backend.enums.AccountType;
import lombok.Data;

@Data
public class RegisterData {
    private String email;
    private String username;
    private String password;
    private AccountType accountType;
}
