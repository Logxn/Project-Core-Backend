package com.heroku.backend.service;

import com.heroku.backend.data.LoginData;
import com.heroku.backend.data.response.LoginResponseData;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.PasswordMismatchException;
import com.heroku.backend.exceptions.UserNotFoundException;
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

    @Value("${encryption.salt}")
    private String salt;
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public LoginService(EmailRepository emailRepository, UsersRepository usersRepository){
        this.emailRepository = emailRepository;
        this.usersRepository = usersRepository;
    }

    public ResponseEntity<LoginResponseData> login(@RequestBody LoginData loginData) throws MissingParameterException, UserNotFoundException, PasswordMismatchException {
        String email = loginData.getEmail();
        String password = loginData.getPassword();

        if(email == null || password == null)
            throw new MissingParameterException();

        UserEntity foundUser = usersRepository.findByEmail(email);

        if(foundUser == null)
            throw new UserNotFoundException();

        String encryptedPassword = foundUser.getEncryptedPassword();
        String decryptedPassword = decryptPassword(encryptedPassword);

        if(encryptedPassword != decryptedPassword)
            throw new PasswordMismatchException();

        LoginResponseData loginResponse = new LoginResponseData(LocalDateTime.now());
        loginResponse.setStatus(Status.SUCCESS);

        //ToDo: Add Auth-Token system per user, with refresh-token and expiry time
        return ResponseEntity.ok(loginResponse);
    }

    // Like I said - I might move this to a separate class
    public String decryptPassword(String encryptedPassword){
        MessageDigest sha = null;

        try{
            key = salt.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedPassword)));
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
