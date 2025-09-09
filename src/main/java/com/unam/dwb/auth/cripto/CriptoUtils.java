package com.unam.dwb.auth.cripto;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CriptoUtils {
	
	private static final String ALGORITMO = "AES";
    private static final String MODO_CIFRADO = "AES/GCM/NoPadding";
    

    private static final byte[] AES_KEY = {
    		(byte) 0xF4, (byte) 0x1A, (byte) 0x3C, (byte) 0xB5, (byte) 0x89, (byte) 0xFF, (byte) 0x72, (byte) 0xE1,
    		(byte) 0x2D, (byte) 0x37, (byte) 0x99, (byte) 0x0A, (byte) 0x54, (byte) 0xC3, (byte) 0x6F, (byte) 0x18,
    		(byte) 0xD0, (byte) 0x81, (byte) 0xAB, (byte) 0x47, (byte) 0x3E, (byte) 0x22, (byte) 0x7D, (byte) 0xFC,
    		(byte) 0xB8, (byte) 0xC1, (byte) 0xE5, (byte) 0x2A, (byte) 0x69, (byte) 0x4F, (byte) 0x91, (byte) 0x7B
    		};
    
    private CriptoUtils() {}

    static {
        SecureRandom random = new SecureRandom();
        random.nextBytes(AES_KEY);
    }

    private static Cipher getCipher(int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(AES_KEY, ALGORITMO);
        Cipher cipher = Cipher.getInstance(MODO_CIFRADO);
        cipher.init(mode, secretKey);
        return cipher;
    }

    public static byte[] encrypt(byte[] data) throws Exception {
        return getCipher(Cipher.ENCRYPT_MODE).doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        return getCipher(Cipher.DECRYPT_MODE).doFinal(encryptedData);
    }

    public static String encryptString(String plainText) throws Exception {
        byte[] encrypted = encrypt(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decryptString(String encryptedText) throws Exception {
        byte[] decrypted = decrypt(Base64.getDecoder().decode(encryptedText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

}
