package com.unam.dwb.auth.cripto;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.spec.GCMParameterSpec;

public class CriptoUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; 
    private static final int IV_LENGTH = 12;       

    private static final byte[] AES_KEY = {
            (byte) 0xF4, (byte) 0x1A, (byte) 0x3C, (byte) 0xB5, (byte) 0x89, (byte) 0xFF, (byte) 0x72, (byte) 0xE1,
            (byte) 0x2D, (byte) 0x37, (byte) 0x99, (byte) 0x0A, (byte) 0x54, (byte) 0xC3, (byte) 0x6F, (byte) 0x18,
            (byte) 0xD0, (byte) 0x81, (byte) 0xAB, (byte) 0x47, (byte) 0x3E, (byte) 0x22, (byte) 0x7D, (byte) 0xFC,
            (byte) 0xB8, (byte) 0xC1, (byte) 0xE5, (byte) 0x2A, (byte) 0x69, (byte) 0x4F, (byte) 0x91, (byte) 0x7B
    };

    private CriptoUtils() {}

    public static byte[] encrypt(byte[] data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY, ALGORITHM);
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

        byte[] encrypted = cipher.doFinal(data);
        byte[] result = new byte[IV_LENGTH + encrypted.length];
        System.arraycopy(iv, 0, result, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, result, IV_LENGTH, encrypted.length);
        return result;
    }

    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        if (encryptedData.length < IV_LENGTH) {
            throw new IllegalArgumentException("La longitud del mensaje es incorrecta");
        }

        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY, ALGORITHM);

        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(encryptedData, 0, iv, 0, IV_LENGTH);
        byte[] ciphertext = new byte[encryptedData.length - IV_LENGTH];
        System.arraycopy(encryptedData, IV_LENGTH, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        return cipher.doFinal(ciphertext);
    }

    public static String encryptString(String plainText) throws Exception {
        byte[] encrypted = encrypt(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decryptString(String encryptedText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        byte[] decrypted = decrypt(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}

