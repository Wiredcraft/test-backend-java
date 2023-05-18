package com.jiang.test.backend.utils;

public class DistanceUtils {

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0; // 地球半径（单位：公里）

        // 将经纬度转换为弧度
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 计算经纬度的差值
        double dLon = lon2Rad - lon1Rad;
        double dLat = lat2Rad - lat1Rad;

        // 应用Haversine公式计算距离
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance;
    }


}
