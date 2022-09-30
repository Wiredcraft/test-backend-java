package com.coffee.user;

import com.coffee.user.request.FriendCreateParam;
import com.coffee.user.response.info.FriendInfo;
import com.coffee.user.response.info.UserInfo;
import com.coffee.user.service.FriendService;
import com.coffee.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * user friend 相关接口测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendTest {
    private static final Logger logger = LoggerFactory.getLogger(FriendTest.class);

    @Autowired
    private FriendService friendService;

    @Test
    public void create() {
        FriendCreateParam createParam = new FriendCreateParam();
        createParam.setFriendId("2");
        createParam.setUserId("3");
        friendService.create(createParam);
    }

    @Test
    public void delete() {
        friendService.delete("4");
    }

    @Test
    public void findByUserId() {
        List<FriendInfo> friendInfos = friendService.findByUserId("3");
        logger.info("FriendInfo = {}",friendInfos.toString());
    }
}
