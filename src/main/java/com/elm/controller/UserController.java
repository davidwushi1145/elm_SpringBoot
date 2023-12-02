package com.elm.controller;

import com.elm.common.BaseResponse;
import com.elm.common.ErrorCode;
import com.elm.common.ResultUtils;
import com.elm.service.UserService;
import com.elm.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    /**
     * 用户登录
     * 虽然没有涉及到业务数据的变化，但是登录还是要用post方法以掩盖敏感信息
     *
     * @param userId
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public BaseResponse<Map<String, String>> getUserByIdByPass(@RequestParam("userId") String userId, @RequestParam("password") String password) throws Exception {
        if (StringUtils.isAnyBlank(userId, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        Map<String, String> result = userService.getUserByIdByPass(userId, password);
        if (!result.isEmpty()) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，用户登录失败");
        }
    }

    /**
     * 避免重复用户
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/{userId}")
    public BaseResponse<Integer> getUserById(@PathVariable(value = "userId") String userId) throws Exception {
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        Integer result = userService.getUserById(userId);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，查询用户失败");
        }
    }

    @PostMapping("/register")
    public BaseResponse<Integer> saveUser(@RequestParam("userId") String userId,
                                          @RequestParam("password") String password,
                                          @RequestParam("userName") String userName,
                                          @RequestParam("userSex") Integer userSex) throws Exception {
        System.out.println(userId + password + userName + userSex);
        if (StringUtils.isAnyBlank(userId, password, userName) || password == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度过短，应大于6位");
        }
        Integer result = userService.saveUser(userId, password, userName, userSex);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，新增用户失败");
        }
    }
}
