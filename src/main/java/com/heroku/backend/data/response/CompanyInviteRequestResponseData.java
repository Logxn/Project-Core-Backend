package com.heroku.backend.data.response;

import com.heroku.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
public class CompanyInviteRequestResponseData {
    public Status status;
    public String inviteLink;
    public LocalDateTime timestamp;
}
