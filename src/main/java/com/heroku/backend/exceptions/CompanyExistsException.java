package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A company with this name already exists!")
public class CompanyExistsException extends Exception {
    public CompanyExistsException(){
        super();
    }
}
