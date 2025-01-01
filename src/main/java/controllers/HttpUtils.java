package controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import services.AuthService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HttpUtils {

    private final static AuthService authService = new AuthService();

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

    public static UUID checkSessionMiddleware(HttpExchange exchange) throws SQLException {
        Map<String, String> cookiesMap = HttpUtils.parseCookies(exchange.getRequestHeaders());
        String sessionId = cookiesMap.get("session_id");

        if (sessionId != null) {
            try {
                return authService.isUserLoggedIn(UUID.fromString(sessionId));
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    public static UUID protectedRoutesMiddleware(HttpExchange exchange) throws IOException {
        UUID loggedUserId = null;
        try {
            loggedUserId = HttpUtils.checkSessionMiddleware(exchange);
        } catch (SQLException e) {
            exchange.getResponseHeaders().set("Location", "/500");
            exchange.sendResponseHeaders(302, -1);
        }
        if (loggedUserId != null) {
            return loggedUserId;
        } else {
            exchange.getResponseHeaders().set("Location", "/sign-in");
            exchange.sendResponseHeaders(302, -1);
            return null;
        }
    }

    public static void avoidDuplicateLoginMiddleware(HttpExchange exchange) throws IOException {
        UUID loggedIn = null;
        try {
            loggedIn = HttpUtils.checkSessionMiddleware(exchange);
        } catch (SQLException e) {
            exchange.getResponseHeaders().set("Location", "/500");
            exchange.sendResponseHeaders(302, -1);
        }
        if (loggedIn != null) {
            exchange.getResponseHeaders().set("Location", "/files/root");
            exchange.sendResponseHeaders(302, -1);
        }
    }
}
