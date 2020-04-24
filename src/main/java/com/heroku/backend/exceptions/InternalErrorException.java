package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "We're sorry. Something went wrong on our side!")
public class InternalErrorException extends Exception {
    public InternalErrorException(){
        super();
    }
}
