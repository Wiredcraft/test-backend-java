package com.coffee.user;

import com.coffee.user.request.UserGeohashCreateParam;
import com.coffee.user.response.info.NearbyFriendsInfo;
import com.coffee.user.service.UserGeoHashService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * user定位及附近的人 测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserGeohashTest {
    private static final Logger logger = LoggerFactory.getLogger(UserGeohashTest.class);
    @Autowired
    private UserGeoHashService userGeoHashService;

    //103.752457 36.068718 兰州
    //121.478889，31.271572 上海
    //120.0439,29.363371 义乌
    //119.635918 ,29.110464 金华
    //120.24191，29.28946 东阳
    @Test
    public void createOrUpdateUserGeohash() {
//        UserGeohashCreateParam createParam = new UserGeohashCreateParam();
//        createParam.setUserId("1");
//        createParam.setLongitude(new Double("120.24191"));
//        createParam.setLatitude(new Double("29.28946"));
//        userGeoHashService.createOrUpdateUserGeohash(createParam);
//
//        UserGeohashCreateParam createParam1 = new UserGeohashCreateParam();
//        createParam1.setUserId("2");
//        createParam1.setLongitude(new Double("119.635918"));
//        createParam1.setLatitude(new Double("29.110464"));
//        userGeoHashService.createOrUpdateUserGeohash(createParam1);
//
//        UserGeohashCreateParam createParam2 = new UserGeohashCreateParam();
//        createParam2.setUserId("3");
//        createParam2.setLongitude(new Double("103.752457"));
//        createParam2.setLatitude(new Double("36.068718"));
//        userGeoHashService.createOrUpdateUserGeohash(createParam2);
//
//        UserGeohashCreateParam createParam3 = new UserGeohashCreateParam();
//        createParam3.setUserId("4");
//        createParam3.setLongitude(new Double("121.478889"));
//        createParam3.setLatitude(new Double("31.271572"));
//        userGeoHashService.createOrUpdateUserGeohash(createParam3);

        UserGeohashCreateParam createParam4 = new UserGeohashCreateParam();
        createParam4.setUserId("5");
        createParam4.setLongitude(new Double("120.0439"));
        createParam4.setLatitude(new Double("29.363371"));
        userGeoHashService.createOrUpdateUserGeohash(createParam4);

    }

    @Test
    public void findNearbyFriends() {
        NearbyFriendsInfo info = userGeoHashService.findNearbyFriends("2", new Double("200"), 10);
        logger.info("nearbyFriendsInfo={}", info);
    }
}
