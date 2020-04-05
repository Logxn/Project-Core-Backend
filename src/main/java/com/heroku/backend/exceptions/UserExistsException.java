package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already registered")
public class UserExistsException extends Exception {
    public UserExistsException(){
        super();
    }
}