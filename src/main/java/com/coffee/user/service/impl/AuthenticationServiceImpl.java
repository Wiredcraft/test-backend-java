package com.coffee.user.service.impl;


import com.coffee.user.dao.UserDao;
import com.coffee.user.domain.po.UserPO;
import com.coffee.user.exception.BizException;
import com.coffee.user.helper.AuthTokenHelper;
import com.coffee.user.helper.CodeCacheKeyHelper;
import com.coffee.user.request.UserRegisterParam;
import com.coffee.user.request.AuthLoginParam;
import com.coffee.user.request.UserCreateParam;
import com.coffee.user.response.info.UserInfo;
import com.coffee.user.response.info.AuthLoginInfo;
import com.coffee.user.service.AuthenticationService;
import com.coffee.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.coffee.user.enums.ErrorCodeEnum.*;


/**
 * AuthenticationServiceImpl
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private static final String separator = "|";

    private static final String USER_LOGIN_KEY = "user_login_key";


    @Autowired
    private AuthTokenHelper authTokenHelper;

    @Autowired
    private CodeCacheKeyHelper codeCacheKeyHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public AuthLoginInfo login(AuthLoginParam loginParam) {
        // 先check下必填参数
        if (StringUtils.isBlank(loginParam.getName())
                || StringUtils.isBlank(loginParam.getPassword())) {
            logger.warn("用户名或者密码不能为空 userName:{}", loginParam.getName());
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "用户名或者密码不能为空");
        }
        AuthLoginInfo authLoginInfo = new AuthLoginInfo();
        // 防止短时间重复登录
        String key = USER_LOGIN_KEY + separator + loginParam.getName();
        RLock rLock = redissonClient.getLock(key);
        try {
            rLock.lock(2, TimeUnit.SECONDS);

            // 根据用户名查询用户信息
            UserPO userPO = userDao.findByUserName(loginParam.getName());
            if (Objects.isNull(userPO)) {
                logger.error("获取用户信息出错");
                throw new BizException(USER_NOT_FOUND.getErrorCode(), "用户不存在，请先注册");
            }

            // 校验密码
            if (!StringUtils.equals(loginParam.getPassword(), userPO.getPassword())) {
                logger.warn("用户密码校验出错 userId:{}", userPO.getId());
                throw new BizException(USERNAME_PASSWORD_ERROR);
            }
            // 生成token信息
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userPO, userInfo);
            String accessToken = authTokenHelper.generateAccessToken(userInfo);

            BeanUtils.copyProperties(userPO, authLoginInfo);
            authLoginInfo.setAccessToken(accessToken);
        } finally {
            rLock.unlock();
        }
        return authLoginInfo;
    }

    @Override
    public Boolean register(UserRegisterParam registerParam) {
        if (StringUtils.isBlank(registerParam.getCellphone())
                || StringUtils.isBlank(registerParam.getVerifyCode())) {
            logger.warn("手机号验证码不能为空 cellphone:{}", registerParam.getCellphone());
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "手机号验证码不能为空");
        }
        if (StringUtils.isBlank(registerParam.getName())
                || StringUtils.isBlank(registerParam.getPassword())) {
            logger.warn("用户名或者密码不能为空 userName:{}", registerParam.getName());
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "用户名或者密码不能为空");
        }

        //防止短时间内重复注册
        String key = registerParam.getCellphone() + separator + registerParam.getName();
        RLock rLock = redissonClient.getLock(key);
        try {
            rLock.lock(2, TimeUnit.SECONDS);

            // 检查校验码
            // 因无短信验证码流程，此处放过
            codeCacheKeyHelper.checkVerifyCode(
                    registerParam.getCellphone(),
                    registerParam.getVerifyCode()
            );
            // 用户注册
            UserCreateParam createParam = new UserCreateParam();
            BeanUtils.copyProperties(registerParam, createParam);
            UserInfo userInfo = userService.create(createParam);
            if (Objects.isNull(userInfo)) {
                throw new BizException(REGISTER_ERROR.getErrorCode());
            }

        } finally {
            // 释放锁
            rLock.unlock();
        }
        return Boolean.TRUE;
    }

}
