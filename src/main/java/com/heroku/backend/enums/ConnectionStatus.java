package com.heroku.backend.enums;

import lombok.Getter;

@Getter
public enum ConnectionStatus {
    LOGGED_IN("logged_in"),
    LOGGED_OUT("logged_out");

    private String connectionType;

    ConnectionStatus(String accountType) {
        this.connectionType = accountType;
    }
}