package com.elm.service;

import com.elm.model.bo.User;
import com.elm.model.vo.UserVo;

import java.util.Map;

public interface UserService {
    public Map<String, String> getUserByIdByPass(String userId, String password);

    public int getUserById(String userId);

    public int saveUser(String userId, String password, String userName,Integer userSex);

    public UserVo getUserVO(User user);

    public User getUser(String userId);
}
