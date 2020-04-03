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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

import static javax.crypto.Cipher.*;

@Service
public class LoginService {

    private EmailRepository emailRepository;
    private UsersRepository usersRepository;
    private CryptoHelper cryptoHelper;

    @Value("${encryption.salt}")
    private String salt;
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public LoginService(EmailRepository emailRepository, UsersRepository usersRepository){
        this.emailRepository = emailRepository;
        this.usersRepository = usersRepository;
        this.cryptoHelper = new CryptoHelper();
    }

    public ResponseEntity<LoginResponseData> login(@RequestBody LoginData loginData) throws MissingParameterException, InvalidUserPassException {
        String email = loginData.getEmail();
        String password = loginData.getPassword();

        if(email == null || password == null)
            throw new MissingParameterException();

        UserEntity foundUser = usersRepository.findByEmail(email);

        if(foundUser == null)
            throw new InvalidUserPassException();

        String encryptedPassword = foundUser.getEncryptedPassword();
        String decryptedPassword = cryptoHelper.decryptString(encryptedPassword);

        if(password != decryptedPassword)
            throw new InvalidUserPassException();

        LoginResponseData loginResponse = new LoginResponseData(foundUser.getUsername(), LocalDateTime.now());
        loginResponse.setStatus(Status.SUCCESS);

        //ToDo: Add Auth-Token system per user, with refresh-token and expiry time
        return ResponseEntity.ok(loginResponse);
    }

}
