package controllers;

import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpServer;

import entities.UserDao;
import entities.dto.UserDto;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import org.postgresql.util.PSQLException;
import services.AuthService;
import services.BodyParser;
import views.AuthView;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AuthController {

    private final AuthService authService = new AuthService();

    public AuthController (HttpServer server) {

        server.createContext("/sign-in", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = AuthView.generateLoginView();
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }));

        server.createContext("/sign-up", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = AuthView.generateSignUpView();
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        }));


        server.createContext("/api/v1/auth/sign-in", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String response = "";
                int responseCode = 500;
                try {
                    UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
                    UserDao userDao = new UserDao(user.getEmail(), user.getPassword());
                    response = authService.signInUser(userDao);
                    responseCode = 201;
                } catch (InvalidEmailException | InvalidPasswordException e) {
                    response = e.getMessage();
                    responseCode = 400;
                } catch (InvalidCredentialsException e) {
                    response = e.getMessage();
                    responseCode = 401;
                } catch (JsonParseException | IOException e) {
                    response = "Error parsing the request";
                    System.out.println("Error parsing auth request " + e.getMessage());
                    responseCode = 400;
                } catch (PSQLException e) {
                    response = e.getMessage();
                    responseCode = 409;
                } catch (SQLException e) {
                    response = "Internal server error please try again later";
                    System.out.println("Error with SQL query on registering user " + e.getMessage());
                }
                exchange.sendResponseHeaders(responseCode, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/api/v1/auth/sign-up", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String response = "";
                int responseCode = 500;
                try {
                    UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
                    UserDao newUser = new UserDao(user.getEmail(), user.getPassword());
                    authService.singUpUser(newUser);
                    response = "User registered successfully";
                    responseCode = 201;
                } catch (InvalidEmailException | InvalidPasswordException e) {
                    response = e.getMessage();
                    responseCode = 401;
                } catch (JsonParseException | IOException e) {
                    response = "Error parsing the request";
                    System.out.println("Error parsing auth request " + e.getMessage());
                    responseCode = 400;
                } catch (PSQLException e) {
                    response = e.getMessage();
                    responseCode = 409;
                } catch (SQLException e) {
                    response = "Internal server error please try again later";
                    System.out.println("Error with SQL query on registering user " + e.getMessage());
                }
                if (responseCode >= 400) {
                    //TODO: return a toast
                }
                exchange.sendResponseHeaders(responseCode, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/api/v1/auth/log-out", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                //Send the logut page
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
    }
}
