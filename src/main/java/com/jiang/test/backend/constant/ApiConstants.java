package com.jiang.test.backend.constant;

public final class ApiConstants {

    public static final String COMMON_URL = "/api";
    public static final String GET_URL = "/user/getUser/{id}";
    public static final String POST_URL = "/user/addUser";
    public static final String UPDATE_URL = "/user/updateUser";
    public static final String DELETE_URL = "/user/delUser";

    public static final String GET_FOLLOWERS_URL = "/friend/delUser";
    public static final String ADD_FOLLOWERS_URL = "/friend/delUser";
    public static final String REMOVE_FOLLOWERS_URL = "/friend/delUser";
    public static final String GET_FRIENDS_URL = "/friend/delUser";
    public static final String GET_COMMON_FRIENDS_URL = "/friend/delUser";
    public static final String GET_NEARBY_FRIENDS_URL = "/friend/delUser";


    private ApiConstants() {
        // 私有构造函数，防止实例化
    }
}