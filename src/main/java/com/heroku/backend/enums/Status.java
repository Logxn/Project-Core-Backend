package com.heroku.backend.enums;

import lombok.Getter;

@Getter
public enum Status {
    LOGIN("login"),
    REGISTER("register");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
