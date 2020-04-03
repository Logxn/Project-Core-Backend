package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Wrong password received")
public class PasswordMismatchException extends Exception {
    public PasswordMismatchException(){
        super();
    }
}
