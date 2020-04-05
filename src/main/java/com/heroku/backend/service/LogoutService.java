package com.heroku.backend.service;

import com.heroku.backend.MongoDBConfiguration;
import com.heroku.backend.data.LogoutData;
import com.heroku.backend.data.response.LogoutResponseData;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.ConnectionStatus;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserNotLoggedInException;
import com.heroku.backend.repository.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class LogoutService {
    private UsersRepository usersRepository;
    private MongoOperations mongoOperations;

    public LogoutService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoDBConfiguration.class);
        mongoOperations = (MongoOperations) applicationContext.getBean("mongoTemplate");
    }

    public ResponseEntity<LogoutResponseData> logout(@RequestBody LogoutData logoutData) throws MissingParameterException, UserNotLoggedInException {
        String username = logoutData.getUsername();

        if(username == null)
            throw new MissingParameterException();

        UserEntity foundUser = usersRepository.findByUsername(username);

        if(foundUser.getConnectionStatus() == ConnectionStatus.LOGGED_OUT)
            throw new UserNotLoggedInException();

        foundUser.updateConnection(ConnectionStatus.LOGGED_OUT);
        mongoOperations.save(foundUser);

        LogoutResponseData logoutResponseData = new LogoutResponseData(Status.SUCCESS, LocalDateTime.now());
        return ResponseEntity.ok(logoutResponseData);
    }
}
