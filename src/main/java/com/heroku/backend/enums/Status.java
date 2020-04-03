package com.heroku.backend.enums;

import lombok.Getter;

@Getter
public enum Status {
    LOGIN("login"),
    REGISTER("register"),
    SUCCESS("success"),
    FAILED("failed");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
