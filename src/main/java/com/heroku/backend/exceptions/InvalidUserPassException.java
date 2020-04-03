package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Username or password not found")
public class InvalidUserPassException extends Exception {
    public InvalidUserPassException(){
        super();
    }
}
