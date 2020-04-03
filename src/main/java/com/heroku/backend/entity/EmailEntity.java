package com.heroku.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Document(collection = "emails")
public class EmailEntity {

    @Id
    private String id;

    private String email;

    public EmailEntity(String email) {
        this.email = email;
    }
}
