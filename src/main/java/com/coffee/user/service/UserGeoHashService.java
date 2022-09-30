package com.coffee.user.service;

import com.coffee.user.request.UserGeohashCreateParam;
import com.coffee.user.response.info.NearbyFriendsInfo;

public interface UserGeoHashService {

    boolean createOrUpdateUserGeohash(UserGeohashCreateParam createParam);

    NearbyFriendsInfo findNearbyFriends(String userId,
                                        double distance,
                                        int len);
}
