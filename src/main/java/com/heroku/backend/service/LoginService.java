package com.heroku.backend.service;

import com.heroku.backend.CryptoHelper;
import com.heroku.backend.data.LoginData;
import com.heroku.backend.data.response.LoginResponseData;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.InvalidUserPassException;
import com.heroku.backend.repository.EmailRepository;
import com.heroku.backend.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


@Service
public class LoginService {

    private EmailRepository emailRepository;
    private UsersRepository usersRepository;
    private CryptoHelper cryptoHelper;

    public LoginService(EmailRepository emailRepository, UsersRepository usersRepository, CryptoHelper cryptoHelper){
        this.emailRepository = emailRepository;
        this.usersRepository = usersRepository;
        this.cryptoHelper = cryptoHelper;
    }

    public ResponseEntity<LoginResponseData> login(@RequestBody LoginData loginData) throws MissingParameterException, InvalidUserPassException{
        String email = loginData.getEmail();
        String password = loginData.getPassword();

        if(email == null || password == null)
            throw new MissingParameterException();

        UserEntity foundUser = usersRepository.findByEmail(email);

        if(foundUser == null)
            throw new InvalidUserPassException();

        String encryptedPassword = foundUser.getEncryptedPassword();
        String decryptedPassword = cryptoHelper.decryptString(encryptedPassword);

        if(!password.equals(decryptedPassword)){
            throw new InvalidUserPassException();}

        LoginResponseData loginResponse = new LoginResponseData(foundUser.getUsername(), LocalDateTime.now());
        loginResponse.setStatus(Status.SUCCESS);

        //ToDo: Add Auth-Token system per user, with refresh-token and expiry time
        return ResponseEntity.ok(loginResponse);
    }

}
