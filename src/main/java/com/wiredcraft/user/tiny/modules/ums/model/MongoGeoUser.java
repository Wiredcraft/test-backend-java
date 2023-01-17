package com.wiredcraft.user.tiny.modules.ums.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "mongoGeoUser")
@Data
@Builder
public class MongoGeoUser {
    @Id
    private ObjectId id;
    private String name;
    private GeoJsonPoint location;
    private Date createdAt;




}
