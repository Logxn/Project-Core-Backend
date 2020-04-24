package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class EmailResponseData {
    private String email;
    private Status status;
    private LocalDateTime localDateTime;

    public EmailResponseData(String email, LocalDateTime localDateTime) {
        this.email = email;
        this.localDateTime = localDateTime;
    }

    public void setStatus(Status status){ this.status = status; }
}
