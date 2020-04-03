package com.heroku.backend.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    EMPLOYER("employer"),
    EMPLOYEE("employee");

    private String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }
}
