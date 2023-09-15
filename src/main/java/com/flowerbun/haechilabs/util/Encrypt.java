package com.flowerbun.haechilabs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.flowerbun.haechilabs.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Encrypt {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "LkTla3oUhK4TRpHAhbQM7iinZJn5KgvN";
    private static final String IV = "MdSxBGp7heT0VsXK";

    private Encrypt() {}


    public static String oneWay(String target) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(target.getBytes());
        } catch (NoSuchAlgorithmException nae) {
            log.error("암호화 모듈 실행 실패 : {} {}", nae.getMessage(), nae);
        }
        return new String(md.digest());
    }

    static String twoWay(String target) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] bytes = cipher.doFinal(target.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error("TwoWay 암호화 모듈 실패 : {} {} ", e.getMessage(), e);
            throw new CustomException("암호화 실패");
        }
    }

    static String twoWayDecrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            log.error("TwoWay 암호화 모듈 Decrypted 실패 : {} {}", e.getMessage(), e);
            throw new CustomException("복호화 실패");
        }
    }


}
