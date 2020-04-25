package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Company not found!")
public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException(){
        super();
    }
}
