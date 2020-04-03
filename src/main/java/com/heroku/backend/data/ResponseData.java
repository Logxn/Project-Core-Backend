package com.heroku.backend.data;

import com.heroku.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ResponseData {
    private String email;
    private Status status;
    private LocalDateTime localDateTime;

    public ResponseData(String email, LocalDateTime localDateTime) {
        this.email = email;
        this.localDateTime = localDateTime;
    }
}
