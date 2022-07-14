package com.lyt.backend.service;

import com.lyt.backend.daos.UserInfoRepository;
import com.lyt.backend.daos.UserPasswordRepository;
import com.lyt.backend.models.UserPassword;
import com.lyt.backend.models.UserPersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginInfoService implements UserDetailsService {
    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * @param username the username identifying the user whose data is required.
     * @return The user object corresponding to username
     * @throws UsernameNotFoundException when username and password does not match
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userPasswordRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * register a new user
     * @param username
     * @param password
     * @return if register is successful
     */
    @Transactional
    public boolean register(String username, String password) {
        if(userPasswordRepository.existsByName(username)) {
            return false;
        } else {
            UserPassword userPassword = new UserPassword();
            userPassword.setPassword(encoder.encode(password));
            userPassword.setName(username);
            UserPassword save = userPasswordRepository.save(userPassword);
            UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
            userPersonalInfo.setId(save.getId());
            userInfoRepository.save(userPersonalInfo);
            return true;
        }
    }

    /**
     * change one's password
     * @param username username
     * @param newpassword password changeed to
     * @throws UsernameNotFoundException when username does not exist in db
     */
    public void changePassword(String username, String newpassword) {
        userPasswordRepository.findByName(username).map(user -> {
            user.setPassword(encoder.encode(newpassword));
            userPasswordRepository.save(user);
            return user;
        });
    }

    /**
     * unregister user
     * @param username
     */
    public void deRegister(String username) {
        userPasswordRepository.findByName(username).map(user -> {
            userPasswordRepository.deleteById(user.getId());
            return user;
        });
    }
}
