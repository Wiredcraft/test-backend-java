package com.coffee.user.service;


import com.coffee.user.request.UserRegisterParam;
import com.coffee.user.request.AuthLoginParam;
import com.coffee.user.response.info.AuthLoginInfo;

/**
 * AuthenticationService
 *
 */
public interface AuthenticationService {

    /**
     * 登录请求
     *
     * @param loginParam 登录参数
     * @return 登录返回信息
     */
    AuthLoginInfo login(AuthLoginParam loginParam);


    /**
     * 注册请求
     *
     * @param registerParam 注册参数
     * @return 注册结果
     */
    Boolean register(UserRegisterParam registerParam);


//    /**
//     * 修改密码
//     *
//     * @param modifyPasswordParam 修改密码请求
//     * @return 修改结果
//     */
//    Boolean modifyPassword(AuthModifyPasswordParam modifyPasswordParam);



}
