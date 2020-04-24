package com.heroku.backend.data;

import com.heroku.backend.enums.AccountType;
import lombok.Data;

@Data
public class RegisterData {
    private String email;
    private String username;
    private String password;
    private AccountType accountType;

    // While registering the user will be asked if he wants to register as a normal user or with a company
    private CompanyData companyData = null;
}
