package com.wiredcraft.test.user.service.impl;

import com.wiredcraft.test.user.dao.UserRepository;
import com.wiredcraft.test.user.model.po.UserPO;
import com.wiredcraft.test.user.model.req.UserReq;
import com.wiredcraft.test.user.model.vo.UserVO;
import com.wiredcraft.test.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserVO> list() {
        List<UserPO> userPOList = userRepository.findAll();
        List<UserVO> result = new ArrayList<>();
        for (UserPO po : userPOList) {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(po, vo);
            result.add(vo);
        }
        return result;
    }

    @Override
    public UserVO get(int id) {
        UserPO po = userRepository.getById(id);
        if (po == null) {
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    @Override
    public int create(UserReq userReq) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userReq, userPO);
        userRepository.save(userPO);
        return userPO.getId();
    }

    @Override
    public int update(UserReq userReq) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userReq, userPO);
        userRepository.save(userPO);
        return userPO.getId();
    }

    @Override
    public boolean delete(int id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean logicDelete(int id) {
        // TODO: 添加逻辑删除字段，比如deleted：0未删除 1已删除
        return true;
    }

}
