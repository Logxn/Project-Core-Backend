package com.heroku.backend.entity;

import com.heroku.backend.enums.AccountType;
import com.heroku.backend.enums.ConnectionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    private String email;
    private String username;
    private String encryptedPassword;
    private AccountType accountType;
    private ConnectionStatus connectionStatus;

    public UserEntity(String email, String username, String password, AccountType accountType) {
        this.email = email;
        this.username = username;
        this.encryptedPassword = password;
        this.accountType = accountType;
    }

    public void updateConnection(ConnectionStatus connectionStatus){
        this.connectionStatus = connectionStatus;
    }

}