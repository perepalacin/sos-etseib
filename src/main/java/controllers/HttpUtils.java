package controllers;

import com.sun.net.httpserver.Headers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    public static Map<String, String> parseCookies(Headers headers) {
        Map<String, String> cookiesMap = new HashMap<>();
        List<String> cookies = headers.get("Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                String[] cookieParams = cookie.split(";");
                for (String param : cookieParams) {
                    String[] keyValue = param.trim().split("=", 2);
                    if (keyValue.length == 2) {
                        cookiesMap.put(keyValue[0], keyValue[1]);
                    }
                }
            }
        }
        return cookiesMap;
    }
}
