package controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpServer;

import entities.UserDao;
import entities.dto.UserDto;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import gg.jte.TemplateEngine;
import services.AuthService;
import services.BodyParser;
import services.EmailService;
import views.AuthView;
import views.ToastView;

public class AuthController {

    private final AuthService authService = new AuthService();
    private final EmailService emailService = new EmailService();

    public AuthController (HttpServer server, TemplateEngine templateEngine) {

        server.createContext("/", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                //TODO: check auth
                exchange.getResponseHeaders().set("Location", "/files/root");
                exchange.sendResponseHeaders(302, -1);
                exchange.close();
            }
        });

        server.createContext("/sign-in", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = AuthView.generateLoginView(templateEngine);
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
                String response = AuthView.generateSignUpView(templateEngine);
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
                    responseCode = 400;
                } catch (PSQLException e) {
                    response = e.getMessage();
                    responseCode = 409;
                } catch (SQLException e) {
                    response = "Internal server error please try again later";
                    System.out.println("Error with SQL query on registering user " + e.getMessage());
                }
                if (responseCode >= 400) {
                    response = ToastView.generateToast("Ups! Hi ha hagut algun problema", response, "is-danger");
                    exchange.getResponseHeaders().set("HX-Trigger", "triggerToast");
                }
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
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

        server.createContext("/email", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                emailService.sendEmail("perepalacin@gmail.com", 9212);
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
            }
        });
    }
}
