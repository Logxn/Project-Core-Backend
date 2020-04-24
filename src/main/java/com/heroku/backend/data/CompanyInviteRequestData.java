package com.heroku.backend.data;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyInviteRequestData {
    public String username;
    public Date expiration;
}
