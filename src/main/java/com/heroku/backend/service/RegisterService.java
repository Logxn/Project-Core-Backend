package com.heroku.backend.service;

import com.heroku.backend.MongoDBConfiguration;
import com.heroku.backend.data.RegisterData;
import com.heroku.backend.data.response.RegisterResponseData;
import com.heroku.backend.entity.EmailEntity;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.AccountType;
import com.heroku.backend.exceptions.MissingParameterException;
import com.heroku.backend.exceptions.UserExistsException;
import com.heroku.backend.repository.EmailRepository;
import com.heroku.backend.repository.UsersRepository;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
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
import java.util.Arrays;
import java.util.Base64;

@Service
public class RegisterService {

    @Autowired
    private EmailRepository emailRepository;

    private UsersRepository usersRepository;
    private MongoOperations mongoOperations;

    @Value("${encryption.salt}")
    private String salt;

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public RegisterService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoDBConfiguration.class);
        mongoOperations = (MongoOperations) applicationContext.getBean("mongoTemplate");
    }

    public ResponseEntity<RegisterResponseData> register(@RequestBody RegisterData registerData) throws MissingParameterException, UserExistsException {
        String email = registerData.getEmail();
        String username = registerData.getUsername();
        String password = registerData.getPassword();
        AccountType accountType = registerData.getAccountType();

        System.out.println("M: " + email + " U: " + username + " P: " + password + " AT: " + accountType);

        if(email == null || username == null || password == null || accountType == null)
            throw new MissingParameterException();

        EmailEntity foundEmail = emailRepository.findByEmail(email);
        if(foundEmail != null)
            throw new UserExistsException();

        String encryptedPassword = encryptPassword(password);
        RegisterResponseData responseData = new RegisterResponseData(email, username, encryptedPassword, accountType);

        usersRepository.insert(new UserEntity(email, username, encryptedPassword, accountType));

        return ResponseEntity.ok(responseData);
    }

    // Might move this to an external class
    private String encryptPassword(String password){
        MessageDigest sha = null;

        try{
            key = this.salt.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest();
            key = Arrays.copyOf(key, 16);
            this.secretKey = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new InternalException("Error while encrypting password");
        }
    }
}
