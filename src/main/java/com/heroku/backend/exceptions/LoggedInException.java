package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already logged in")
public class LoggedInException extends Exception {
    public LoggedInException(){
        super();
    }
}
