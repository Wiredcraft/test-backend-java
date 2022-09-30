package com.coffee.user.service.impl;

import ch.hsr.geohash.GeoHash;
import com.coffee.user.dao.UserGeohashDao;
import com.coffee.user.domain.po.UserGeohashPO;
import com.coffee.user.exception.BizException;
import com.coffee.user.request.UserGeohashCreateParam;
import com.coffee.user.response.info.NearbyFriendsInfo;
import com.coffee.user.service.UserGeoHashService;
import org.apache.commons.lang3.StringUtils;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.coffee.user.enums.ErrorCodeEnum.ILLEGAL_ARGUMENTS;

@Service
public class UserGeoHashServiceImpl implements UserGeoHashService {
    private static Logger logger = LoggerFactory.getLogger(UserGeoHashServiceImpl.class);

    private SpatialContext spatialContext = SpatialContext.GEO;

    @Autowired
    private UserGeohashDao userGeohashDao;

    /**
     * 用户附近的朋友
     *
     * @param userId
     * @param distance 搜索范围
     * @param len      精度
     * @return
     */
    @Override
    public NearbyFriendsInfo findNearbyFriends(String userId,
                                               double distance,
                                               int len) {
        UserGeohashPO userGeohashPO = userGeohashDao.findByUserId(userId);
        if (userGeohashPO == null) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "未查询到该用户定位信息");
        }
        List<String> nearbyFriendIds = nearBySearch(
                distance,
                len,
                userGeohashPO.getLongitude(),
                userGeohashPO.getLatitude()
        );
        //若前端不是先将定位存数据库，而是实时传入定位信息，则查出的附近的人不会包含用户本身
        //由业务具体实现定
        nearbyFriendIds.remove(userId);

        NearbyFriendsInfo nearbyFriendsInfo = new NearbyFriendsInfo();
        nearbyFriendsInfo.setUserId(userGeohashPO.getUserId());
        nearbyFriendsInfo.setFriendIds(nearbyFriendIds);
        return nearbyFriendsInfo;
    }

    /***
     * 添加用户的位置坐标
     * @return
     */
    @Transactional
    @Override
    public boolean createOrUpdateUserGeohash(UserGeohashCreateParam createParam) {
        if (StringUtils.isBlank(createParam.getUserId())
                || createParam.getLatitude() == null
                || createParam.getLongitude() == null) {
            throw new BizException(ILLEGAL_ARGUMENTS.getErrorCode(), "用户id及经纬度都不能为空");
        }
        //默认精度12位
        String geoHashCode = GeohashUtils.encodeLatLon(createParam.getLatitude(), createParam.getLongitude());

        UserGeohashPO preUserGeohashPO = userGeohashDao.findByUserId(createParam.getUserId());
        if (preUserGeohashPO != null) {
            preUserGeohashPO.setLatitude(createParam.getLatitude());
            preUserGeohashPO.setLongitude(createParam.getLongitude());
            preUserGeohashPO.setGeoCode(geoHashCode);
            preUserGeohashPO.setUpdatedAt(new Date());
            userGeohashDao.update(preUserGeohashPO);
        } else {
            UserGeohashPO userGeohashPO = new UserGeohashPO();
            BeanUtils.copyProperties(createParam, userGeohashPO);
            userGeohashPO.setGeoCode(geoHashCode);
            userGeohashDao.create(userGeohashPO);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取附近x米的人
     *
     * @param distance 距离范围 单位km
     * @param len      geoHash的精度
     * @param userLng  当前经度
     * @param userLat  当前纬度
     * @return json
     */
    public List<String> nearBySearch(double distance,
                                     int len,
                                     double userLng,
                                     double userLat) {
        //根据要求的范围，确定geoHash码的精度，获取到当前用户坐标的geoHash码
        GeoHash geoHash = GeoHash.withCharacterPrecision(userLat, userLng, len);
        //获取到用户周边8个方位的geoHash码
        GeoHash[] adjacent = geoHash.getAdjacent();

        List<GeoHash> geoHashList = new ArrayList<>();
        geoHashList.add(geoHash);
        for (GeoHash adjacentSingle : adjacent) {
            geoHashList.add(adjacentSingle);
        }
        //匹配指定精度的geoHash码
        List<UserGeohashPO> users = new ArrayList<>();
        for (GeoHash geo : geoHashList) {
            logger.info("a={}", geo.toBase32());
            List<UserGeohashPO> pos = userGeohashDao.findByGeoCode(geo.toBase32());
            if (!CollectionUtils.isEmpty(pos)) {
                users.addAll(pos);
            }
        }
        //过滤超出距离的
        List<String> userIds = users.stream()
                .filter(a -> getDistance(a.getLongitude(), a.getLatitude(), userLng, userLat) <= distance)
                .map(UserGeohashPO::getUserId)
                .collect(Collectors.toList());
        return userIds;
    }


    /***
     * 球面中，两点间的距离
     * @param longitude 经度1
     * @param latitude  纬度1
     * @param userLng   经度2
     * @param userLat   纬度2
     * @return 返回距离，单位km
     */
    private double getDistance(Double longitude, Double latitude, double userLng, double userLat) {
        return spatialContext.calcDistance(spatialContext.makePoint(userLng, userLat),
                spatialContext.makePoint(longitude, latitude)) * DistanceUtils.DEG_TO_KM;
    }
}
