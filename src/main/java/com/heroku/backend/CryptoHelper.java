package com.heroku.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class CryptoHelper {
    @Value("${encryption.key}")
    private String key;
    @Value("${encryption.salt}")
    private String salt;

    private byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private IvParameterSpec ivParameterSpec;
    private SecretKeyFactory secretKeyFactory;

    public CryptoHelper(){
        this.ivParameterSpec = new IvParameterSpec(iv);

        try {
            this.secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        }
        catch(NoSuchAlgorithmException e)
        {

        }
    }

    public String encryptString(String input){
        try{
            System.out.println("Encryption Key: " + key);
            System.out.println("Encryption Salt: " + salt);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String decryptString(String encryptedInput){
        try{
            System.out.println("Decryption Key: " + key);
            System.out.println("Decryption Salt: " + salt);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedInput)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}