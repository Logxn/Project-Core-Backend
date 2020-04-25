package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User is already a part of this company!")
public class UserAlreadyInCompanyException extends Exception {
    public UserAlreadyInCompanyException(){
        super();
    }
}