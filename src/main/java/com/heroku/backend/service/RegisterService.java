package com.heroku.backend.service;

import com.heroku.backend.CryptoHelper;
import com.heroku.backend.MongoDBConfiguration;
import com.heroku.backend.data.CompanyData;
import com.heroku.backend.data.RegisterData;
import com.heroku.backend.data.response.RegisterResponseData;
import com.heroku.backend.entity.CompanyEntity;
import com.heroku.backend.entity.EmailEntity;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.AccountType;
import com.heroku.backend.enums.ConnectionStatus;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserExistsException;
import com.heroku.backend.repository.EmailRepository;
import com.heroku.backend.repository.UsersRepository;
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

        UserEntity newUser = new UserEntity(email, username, encryptedPassword, accountType);
        newUser.updateConnection(ConnectionStatus.LOGGED_OUT);

        usersRepository.insert(newUser);
        emailRepository.insert(new EmailEntity(email));

        if(registerData.getCompanyData() != null){
            CompanyData companyData = registerData.getCompanyData();
            UserEntity userEntity = usersRepository.findByUsername(username);

            // 1. Company needs to be created
            // 2. User should be set as administrator
            // This could be done by setting "private String AdministratorId" to the user-id
            // This is also the reason why we do this after user-creation

            CompanyEntity companyEntity = new CompanyEntity();
        }

        return ResponseEntity.ok(responseData);
    }

}
