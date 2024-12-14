package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import annotations.Required;
import exceptions.MissingFieldsException;

public class BodyParser {

    public static <T> T parseRequestBody(HttpExchange exchange, Class<T> type) throws IOException, MissingFieldsException {
        InputStream requestBody = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String body = sb.toString();

        Map<String, String> params = parseUrlEncoded(body);

        T obj;
        try {
            obj = type.getDeclaredConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                String value = params.get(field.getName());
                if (value != null) {
                    Object convertedValue = convertValue(field.getType(), value);
                    field.set(obj, convertedValue);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IOException("Failed to instantiate object of type " + type.getName(), e);
        }
        validateRequiredFields(obj, type);
        return obj;
    }

    public static Map<String, String> parseUrlEncoded(String body) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] idx = pair.split("=");
            if (idx.length == 2) {
                String key = URLDecoder.decode(idx[0], "UTF-8");
                String value = URLDecoder.decode(idx[1], "UTF-8");
                params.put(key, value);
            } else if (idx.length == 1) {
                String key = URLDecoder.decode(idx[0], "UTF-8");
                params.put(key, "");
            }
        }
        return params;
    }

    public static Object convertValue(Class<?> type, String value) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        }
        return null;
    }

    public static <T> void validateRequiredFields(T obj, Class<T> type) throws MissingFieldsException {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Required.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        throw new MissingFieldsException("Missing required field: " + field.getName());
                    }
                } catch (IllegalAccessException e) {
                    throw new MissingFieldsException("Missing required field: " + field.getName());
                }
            }
        }
    }
}