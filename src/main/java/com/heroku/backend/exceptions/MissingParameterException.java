package com.heroku.backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Invalid or missing parameter")
public class MissingParameterException extends Exception {
    public MissingParameterException(){
        super();
    }
}
