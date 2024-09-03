package com.solidgate.userservice.utils;

import com.solidgate.userservice.entity.User;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

public class TestUtils {
    public static Integer USER_ID1 = 1;
    public static Integer USER_ID2 = 2;
    public static Integer USER_ID3 = 3;
    public static Integer USER_ID4 = 55;
    public static Integer USER_BALANCE1 = 100;
    public static Integer USER_BALANCE2 = 200;
    public static Integer USER_BALANCE3 = 300;
    public static String USER_NAME = "Yura";
    public static String NULL_BALANCE_ERROR_MESSAGE = "must not be null";
    public static Integer BATCH_SIZE = 2;

    public static User buildUser() {
        return User.builder().id(USER_ID1)
                .balance(INTEGER_ZERO)
                .name(USER_NAME)
                .build();
    }
}
