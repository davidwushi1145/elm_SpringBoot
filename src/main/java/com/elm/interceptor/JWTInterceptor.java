package com.elm.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.elm.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的令牌
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        if (token == null || token.isEmpty()) {
            map.put("state", false);
            map.put("msg", "未提供Token或Token为空");
            sendErrorResponse(response, map);
            return false;
        }
        try {
            JWTUtil.verify(token); // 验证Token
            return true;
        } catch (TokenExpiredException e) {
            map.put("state", false);
            map.put("msg", "Token已经过期");
        } catch (SignatureVerificationException e) {
            map.put("state", false);
            map.put("msg", "签名错误");
        } catch (AlgorithmMismatchException e) {
            map.put("state", false);
            map.put("msg", "加密算法不匹配");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "无效token");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
    private void sendErrorResponse(HttpServletResponse response, Map<String, Object> errorMap) throws IOException {
        String json = new ObjectMapper().writeValueAsString(errorMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }

}
