package com.elm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JWTUtil {
    public static final String SING = "114514";

    private final Map<String, Object> header = new HashMap<String, Object>();

    public JWTUtil() {
        header.put("alg", "HS256");
        header.put("typ", "JWT");
    }

    /**
     * 生成 token
     *
     * @param map
     * @return
     */
    public String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 300);
        JWTCreator.Builder builder = JWT.create();
        map.put("id", UUID.randomUUID().toString());
        //原表达为：map.forEach((k,v)->builder.withClaim(k,v));
        map.forEach(builder::withClaim);
        return builder
                .withHeader(header)
                .withIssuedAt(new Date())
                .withExpiresAt(instance.getTime())//设置过期时间
                .sign(Algorithm.HMAC256(SING));
    }

    /**
     * 检验token合法性
     *
     * @param token
     * @return
     */
    public static String verify(String token) {
        // 使用相同的秘钥解码JWT
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);

        // 从JWT的负载中获取userId声明

        return jwt.getClaim("userId").asString();
    }



}


