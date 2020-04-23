package com.heroku.backend.service;

import com.heroku.backend.CryptoHelper;
import com.heroku.backend.MongoDBConfiguration;
import com.heroku.backend.data.LoginData;
import com.heroku.backend.data.response.LoginResponseData;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.ConnectionStatus;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.LoggedInException;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.InvalidUserPassException;
import com.heroku.backend.repository.EmailRepository;
import com.heroku.backend.repository.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Date;


@Service
public class LoginService {

    private EmailRepository emailRepository;
    private UsersRepository usersRepository;
    private JwtTokenService jwtTokenService;
    private CryptoHelper cryptoHelper;
    private MongoOperations mongoOperations;

    public LoginService(EmailRepository emailRepository, UsersRepository usersRepository, JwtTokenService jwtTokenService, CryptoHelper cryptoHelper){
        this.emailRepository = emailRepository;
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
        this.cryptoHelper = cryptoHelper;

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoDBConfiguration.class);
        mongoOperations = (MongoOperations) applicationContext.getBean("mongoTemplate");
    }

    public ResponseEntity<LoginResponseData> login(@RequestBody LoginData loginData)
            throws MissingParameterException, InvalidUserPassException, LoggedInException {
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

        if(foundUser.getConnectionStatus() == ConnectionStatus.LOGGED_IN)
            throw new LoggedInException();

        foundUser.updateConnection(ConnectionStatus.LOGGED_IN);
        mongoOperations.save(foundUser);

        String accessToken = jwtTokenService.generateToken(foundUser.getUsername());
        Date expirationTime = jwtTokenService.getExpirationDateFromToken(accessToken);

        LoginResponseData loginResponse = new LoginResponseData(foundUser.getUsername(), accessToken, expirationTime, LocalDateTime.now());
        loginResponse.setStatus(Status.SUCCESS);

        return ResponseEntity.ok(loginResponse);
    }

}
