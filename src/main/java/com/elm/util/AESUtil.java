package com.elm.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static AESUtil instance;
    private static final int SEED_LENGTH = 16; // 定义种子字符串的长度
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private final Cipher decryptCipher;
    private final Cipher encryptCipher;
    private final String seed;

    private AESUtil() throws Exception {
        this.seed = generateSeed(SEED_LENGTH); // 自动生成种子字符串
        decryptCipher = Cipher.getInstance(KEY_ALGORITHM);
        encryptCipher = Cipher.getInstance(KEY_ALGORITHM);
        decryptCipher.init(Cipher.DECRYPT_MODE, this.getSecretKey());
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.getSecretKey());
    }
    public static synchronized AESUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new AESUtil();
        }
        return instance;
    }

    private String generateSeed(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytesToHex(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_CHAR[v >>> 4];
            hexChars[i * 2 + 1] = HEX_CHAR[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String decrypt(String content) throws Exception {
        byte[] bytes = Base64.decodeBase64(content);
        byte[] result = decryptCipher.doFinal(bytes);
        return new String(result, StandardCharsets.UTF_8);
    }

    public String encrypt(String content) throws Exception {
        byte[] result = encryptCipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(result);
    }

    public SecretKey getSecretKey() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(128, random);
        return kg.generateKey();
    }

}
