package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Request token already expired")
public class ExpiredTokenException extends Exception {
    public ExpiredTokenException(){
        super();
    }
}
