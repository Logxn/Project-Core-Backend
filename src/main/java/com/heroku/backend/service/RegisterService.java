package com.heroku.backend.service;

import com.heroku.backend.CryptoHelper;
import com.heroku.backend.MongoDBConfiguration;
import com.heroku.backend.data.RegisterData;
import com.heroku.backend.data.response.RegisterResponseData;
import com.heroku.backend.entity.EmailEntity;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.AccountType;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserExistsException;
import com.heroku.backend.repository.EmailRepository;
import com.heroku.backend.repository.UsersRepository;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class RegisterService {

    private EmailRepository emailRepository;
    private UsersRepository usersRepository;
    private MongoOperations mongoOperations;
    private CryptoHelper cryptoHelper;

    @Value("${encryption.salt}")
    private String salt;

    public RegisterService(UsersRepository usersRepository, EmailRepository emailRepository, CryptoHelper cryptoHelper) {
        this.cryptoHelper = cryptoHelper;
        this.emailRepository = emailRepository;
        this.usersRepository = usersRepository;
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoDBConfiguration.class);
        mongoOperations = (MongoOperations) applicationContext.getBean("mongoTemplate");
    }

    public ResponseEntity<RegisterResponseData> register(@RequestBody RegisterData registerData) throws MissingParameterException, UserExistsException {
        String email = registerData.getEmail();
        String username = registerData.getUsername();
        String password = registerData.getPassword();
        AccountType accountType = registerData.getAccountType();

        if(email == null || username == null || password == null || accountType == null)
            throw new MissingParameterException();

        EmailEntity foundEmail = emailRepository.findByEmail(email);
        if(foundEmail != null)
            throw new UserExistsException();

        String encryptedPassword = cryptoHelper.encryptString(password);
        RegisterResponseData responseData = new RegisterResponseData(LocalDateTime.now());
        responseData.setStatus(Status.SUCCESS);

        usersRepository.insert(new UserEntity(email, username, encryptedPassword, accountType));
        emailRepository.insert(new EmailEntity(email));

        return ResponseEntity.ok(responseData);
    }

}
