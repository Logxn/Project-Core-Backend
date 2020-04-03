package com.heroku.backend.data.response;

import com.heroku.backend.enums.AccountType;
import com.heroku.backend.enums.Status;
import lombok.Data;

@Data
public class RegisterResponseData {

    private String username;
    private String email;
    private String password;
    private AccountType accountType;
    private Status status;

    public RegisterResponseData(String username, String email, String encryptedPassword, AccountType accountType){
        this.username = username;
        this.email = email;
        this.password = encryptedPassword;
        this.accountType = accountType;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
