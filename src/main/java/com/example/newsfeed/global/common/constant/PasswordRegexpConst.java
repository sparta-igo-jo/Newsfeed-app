package com.example.newsfeed.global.common.constant;

public class PasswordRegexpConst {

    private PasswordRegexpConst() {
    }

    public static final String PASSWORD_REGEXP_CONST ="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
}
