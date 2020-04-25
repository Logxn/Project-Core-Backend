package com.heroku.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heroku.backend.enums.AccountType;
import com.heroku.backend.enums.ConnectionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Data
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    @JsonIgnore
    private String email;
    private String username;
    @JsonIgnore
    private String encryptedPassword;
    private AccountType accountType;
    private ConnectionStatus connectionStatus;
    @JsonIgnore
    private String company = null;

    public UserEntity(String email, String username, String password, AccountType accountType) {
        this.email = email;
        this.username = username;
        this.encryptedPassword = password;
        this.accountType = accountType;
    }

    public void updateConnection(ConnectionStatus connectionStatus){
        this.connectionStatus = connectionStatus;
    }
    public void updateCompany(String company) { this.company = company; }
}