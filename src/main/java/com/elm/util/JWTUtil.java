package com.elm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.*;

public class JWTUtil {
    public static final String SING = "114514";
    private AESUtil aesUtil; // AES加密工具

    private final Map<String, Object> header = new HashMap<String, Object>();

    public JWTUtil() {
        try {
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            aesUtil = AESUtil.getInstance(); // 获取AESUtil的单例实例
        } catch (Exception e) {
            // 处理AESUtil初始化异常
            e.printStackTrace();
        }
    }

    /**
     * 生成并加密 token
     *
     * @param map
     * @return 加密的token，如果发生异常则返回null
     */
    public String getToken(Map<String, String> map) {
        try {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE, 300);
            JWTCreator.Builder builder = JWT.create();
            map.put("id", UUID.randomUUID().toString());
            map.forEach(builder::withClaim);
            String token = builder
                    .withHeader(header)
                    .withIssuedAt(new Date())
                    .withExpiresAt(instance.getTime()) // 设置过期时间
                    .sign(Algorithm.HMAC256(SING));

            return aesUtil.encrypt(token); // 使用AES加密Token
        } catch (Exception e) {
            // 处理Token生成和加密异常
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证并解密token
     *
     * @param encryptedToken 加密的token
     * @return 解密后的userId，如果发生异常则返回null
     */
    public String verify(String encryptedToken) {
        try {
            String token = aesUtil.decrypt(encryptedToken); // 使用AES解密Token

            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SING)).build().verify(token); // 使用相同的秘钥解码JWT

            return jwt.getClaim("userId").asString(); // 从JWT的负载中获取userId声明
        } catch (Exception e) {
            // 处理Token验证和解密异常
            e.printStackTrace();
            return null;
        }
    }
}
