package com.wiredcraft.user.tiny.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiredcraft.user.tiny.common.exception.Asserts;
import com.wiredcraft.user.tiny.domain.AdminUserDetails;
import com.wiredcraft.user.tiny.modules.ums.dto.UserParam;
import com.wiredcraft.user.tiny.modules.ums.dto.UpdateAdminPasswordParam;
import com.wiredcraft.user.tiny.modules.ums.mapper.UserLoginLogMapper;
import com.wiredcraft.user.tiny.modules.ums.mapper.UserMapper;
import com.wiredcraft.user.tiny.modules.ums.model.MongoGeoUser;
import com.wiredcraft.user.tiny.modules.ums.model.User;
import com.wiredcraft.user.tiny.modules.ums.model.UserLoginLog;
import com.wiredcraft.user.tiny.modules.ums.service.UserService;
import com.wiredcraft.user.tiny.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * user operation logic implementation
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserLoginLogMapper loginLogMapper;


    @Autowired
    private InMemoryUserDetailsManager manager;


    @Autowired
    private MongoTemplate mongoTemplate;



    @Override
    public User getUserByUsername(String username) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername,username);
        List<User> adminList = list(wrapper);
        if (adminList != null && adminList.size() > 0) {
            User admin = adminList.get(0);

            return admin;
        }
        return null;
    }

    @Override
    public void reportLocation(String username, Double x, Double y) {
        Query query = new Query(Criteria.where("name").is(username));
        MongoGeoUser user= mongoTemplate.findOne(query, MongoGeoUser.class);
        if (user == null) {

            MongoGeoUser mongoGeoUser = MongoGeoUser.builder()
                    .location(new GeoJsonPoint(x, y))
                    .name(username)
                    .createdAt(new Date())
                    .build();

            mongoTemplate.save(mongoGeoUser);
        }else {
            user.setLocation(new GeoJsonPoint(x, y));
            mongoTemplate.save(user);
        }

    }

    @Override
    public Boolean exist(String username) {

        return getUserByUsername(username)!=null;
    }

    @Override
    public User register(UserParam userParam) {
        User umsAdmin = new User();
        BeanUtils.copyProperties(userParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        // Check whether users with the same user name exist
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername,umsAdmin.getUsername());
        List<User> umsAdminList = list(wrapper);
        if (umsAdminList.size() > 0) {
            return null;
        }
        // Encrypt the password
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        baseMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;

        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("Incorrect password");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("The account has been disabled");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            if (!manager.userExists(username) ) {
                manager.createUser(userDetails);
            }

            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * Add login record
     * @param username
     */
    private void insertLoginLog(String username) {
        User admin = getUserByUsername(username);
        if(admin==null) return;
        UserLoginLog loginLog = new UserLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }



    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public Page<User> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = wrapper.lambda();
        if(StrUtil.isNotEmpty(keyword)){
            lambda.like(User::getUsername,keyword);
            lambda.or().like(User::getNickName,keyword);
        }
        return page(page,wrapper);
    }

    @Override
    public boolean update(Long id, User admin) {
        admin.setId(id);
        User rawAdmin = getById(id);
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            // If the password is the same as the original encrypted password, you do not need to change it
            admin.setPassword(null);
        }else{
            // If the password is different from the original encryption password, the password must be encrypted and changed
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        boolean success = updateById(admin);
        return success;
    }

    @Override
    public boolean delete(Long id) {

        boolean success = removeById(id);

        return success;
    }







    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername,param.getUsername());
        List<User> adminList = list(wrapper);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        User umsAdmin = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(umsAdmin);

        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        User admin = getUserByUsername(username);
        if (admin != null) {

            return new AdminUserDetails(admin);
        }
        throw new UsernameNotFoundException("The user name or password is incorrect");
    }


}
