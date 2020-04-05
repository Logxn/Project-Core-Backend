package com.heroku.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User not logged in")
public class UserNotLoggedInException extends Exception {
    public UserNotLoggedInException(){
        super();
    }
}
