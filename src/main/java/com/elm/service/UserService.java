package com.elm.service;

import java.util.Map;

public interface UserService {
    public Map<String, String> getUserByIdByPass(String userId, String password);

    public int getUserById(String userId);

    public int saveUser(String userId, String password, String userName,Integer userSex);

}
