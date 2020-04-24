package com.heroku.backend.service;

import com.heroku.backend.CryptoHelper;
import com.heroku.backend.data.CompanyData;
import com.heroku.backend.data.RegisterData;
import com.heroku.backend.data.response.RegisterResponseData;
import com.heroku.backend.entity.CompanyEntity;
import com.heroku.backend.entity.EmailEntity;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.AccountType;
import com.heroku.backend.enums.ConnectionStatus;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.CompanyExistsException;
import com.heroku.backend.exceptions.InternalErrorException;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserExistsException;
import com.heroku.backend.repository.CompanyRepository;
import com.heroku.backend.repository.EmailRepository;
import com.heroku.backend.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class RegisterService {

    private EmailRepository emailRepository;
    private UsersRepository usersRepository;
    private CompanyRepository companyRepository;
    private CryptoHelper cryptoHelper;

    public RegisterService(UsersRepository usersRepository, EmailRepository emailRepository, CompanyRepository companyRepository, CryptoHelper cryptoHelper) {
        this.cryptoHelper = cryptoHelper;
        this.emailRepository = emailRepository;
        this.usersRepository = usersRepository;
        this.companyRepository = companyRepository;

    }

    public ResponseEntity<RegisterResponseData> register(@RequestBody RegisterData registerData) throws MissingParameterException, UserExistsException, CompanyExistsException, InternalErrorException {
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

        if(registerData.getCompanyData() != null && accountType == AccountType.EMPLOYER){

            // 1. Company needs to be created.
            // 2. User should be set as administrator.
            // This could be done by setting "private String AdministratorId" to the user-id.
            // This is also the reason why we do this after user-creation.

            CompanyData companyData = registerData.getCompanyData();

            CompanyEntity foundCompany = companyRepository.findByRegex(companyData.getCompanyName());
            if(foundCompany != null)
                throw new CompanyExistsException();

            UserEntity userEntity = usersRepository.findByUsername(username);

            CompanyEntity companyEntity = new CompanyEntity(companyData.getCompanyName(), userEntity.getId(), new String[] { userEntity.getId() });
            companyRepository.insert(companyEntity);
        }

        return ResponseEntity.ok(responseData);
    }

}
