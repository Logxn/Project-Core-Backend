package com.heroku.backend.data;

import com.heroku.backend.enums.AccountType;
import lombok.Data;

@Data
public class RegisterData {
    private String email;
    private String username;
    private String password;
    private AccountType accountType;

    public RegisterData(String email, String username, String password, AccountType accountType)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }
}
