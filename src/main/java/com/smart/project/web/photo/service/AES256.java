package com.smart.project.web.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AES256 {

    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String key = "51404786380552552405118897692714";
    private static final String iv = key.substring(0,16);

    @SneakyThrows
    public String encrypt(String plainText) {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"AES");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ips);

        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String cipherText = Base64.getUrlEncoder().encodeToString(encrypted);
        return cipherText;
    }

    @SneakyThrows
    public String decrypt(String cipherText) {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"AES");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ips);

        byte[] decryptBytes = Base64.getUrlDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decryptBytes);
        String plainText = new String(decrypted, "UTF-8");
        return plainText;
    }
}
