package com.heroku.backend.service;

import com.heroku.backend.entity.EmailEntity;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmailCheckService
{
    public ResponseEntity<Map<String, Object>> checkEmail(String email)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, Object> response = new LinkedHashMap<>();

        /*
            Basic Idea:
            When registering a company/user account - the frontend will ask for an email first
            The frontend will then send a request to /user/checkEmail to see the current Status of this email.

            If it exists in the database: it should come back with something like this
            {"email":email, "status":"login", "timestamp":currentTimestamp}

            If its not in the database: it should come back with something like this
            {"email":email, "status":"register", "timestamp":currentTimestamp}

            Either way - we will send back an OK response to the user.
            The frontend will then decide how to continue with this email -> register or login.

            No Authentication required.

            Document idea:
            {
                "_id": {
                    "$oid": "-snip-"
                },
                "email": "foo@loganthompson.de"
            }

         */

        // Below is a fake object
        // emailEntity will be a returned result from the database.
        EmailEntity emailEntity = new EmailEntity(null);

        response.put("email", email);
        response.put("status", "login");

        // Assuming the email wasn't found in the database
        // We might get a null object (?)
        if(emailEntity == null)
            response.replace("status", "register");

        response.put("timestamp", dateTimeFormatter.format(localDateTime));
        return ResponseEntity.ok(response);
    }
}
