package com.wiredcraft.user.tiny;

import com.wiredcraft.user.tiny.modules.ums.model.MongoGeoUser;
import com.wiredcraft.user.tiny.modules.ums.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void createMongoGeoUsers() {
        MongoGeoUser mongoGeoUser = MongoGeoUser.builder().name("admin").
                location(new GeoJsonPoint(110.3433532254, 21.9248353725)).createdAt(new Date()).build();

        mongoTemplate.save(mongoGeoUser);
        for (int i = 0; i < 6; i++) {
            BigDecimal lng = new BigDecimal(Math.random() * (130 - 100) + 100).setScale(10, BigDecimal.ROUND_HALF_UP);
            BigDecimal lat = new BigDecimal(Math.random() * 20 + 20).setScale(10, BigDecimal.ROUND_HALF_UP);

            MongoGeoUser mongoGeoUser2 = MongoGeoUser.builder().name("user"+i).
                    location(new GeoJsonPoint(lng.doubleValue(), lat.doubleValue())).createdAt(new Date()).build();

            mongoTemplate.save(mongoGeoUser2);
        }

    }

    @Test
    public void nearSphere() {
        Query query = new Query(Criteria.where("location")
                .nearSphere(new GeoJsonPoint(110.3433532254, 21.9248353725))
                .maxDistance(5000));
        query.addCriteria(Criteria.where("name").is("user1"));
        List<MongoGeoUser> venues = mongoTemplate.find(query, MongoGeoUser.class);

        for (MongoGeoUser venue : venues) {
            System.out.println(venue);
        }
    }
    @Test
    public void nearSphereWithSpec() {
        Query query = new Query(

                Criteria.where("").andOperator(
                        Criteria.where("location")
                                .nearSphere(new GeoJsonPoint(110.3433532254, 21.9248353725))
                                .maxDistance(5000),
                        Criteria.where("name").is("user1")
                ));
        List<MongoGeoUser> venues = mongoTemplate.find(query, MongoGeoUser.class);

        for (MongoGeoUser venue : venues) {
            System.out.println(venue);
        }
    }
    @Test
    public void testReport(){
        userService.reportLocation("yuao",110.3433532254, 21.9248353725);
    }
}
