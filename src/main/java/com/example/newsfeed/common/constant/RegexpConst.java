package com.example.newsfeed.common.constant;

public class RegexpConst {

    public static final String EMAIL_REGEXP_CONST = "^[a-zA-Z0-9-_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static final String PASSWORD_REGEXP_CONST = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";

    private RegexpConst() {
    }
}
