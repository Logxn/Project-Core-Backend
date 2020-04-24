package com.heroku.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String email;

    public EmailEntity(String email) {
        this.email = email;
    }
}
