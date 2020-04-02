package com.heroku.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Document(collection = "emails") // I guess this is where I will save them
public class EmailEntity {

    @Id
    private String id;

    private String email;
    private String status;

    public EmailEntity(String email, String status)
    {
        this.email = email;
        this.status = status;
    }

    public String getStatus()
    {
        return this.status;
    }
}
