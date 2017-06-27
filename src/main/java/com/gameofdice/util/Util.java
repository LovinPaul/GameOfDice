package com.gameofdice.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

    public static boolean InvalidSession(HttpServletRequest request) {
        return request.getSession(false) == null;
    }

}
