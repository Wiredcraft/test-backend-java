package com.test.backend.java.service.impl;

import com.test.backend.java.entity.Friend;
import com.test.backend.java.mapper.FriendMapper;
import com.test.backend.java.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rock Jiang
 * @since 2022-04-16
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

}
