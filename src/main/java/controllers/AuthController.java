package controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exceptions.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.postgresql.util.PSQLException;

import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpServer;

import entities.UserDao;
import entities.dto.UserDto;
import gg.jte.TemplateEngine;
import services.AuthService;
import services.BodyParser;
import services.EmailService;
import views.AuthView;
import views.ToastView;

public class AuthController {

    private final AuthService authService = new AuthService();

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
                    authService.signInUser(userDao);
                    exchange.getResponseHeaders().set("Location", "/files/root");
                    exchange.sendResponseHeaders(302, -1);
                } catch (UserNotActivatedException e) {
                    response = AuthView.generateCorrectSignUpResponse(templateEngine);
                    responseCode = 200;
                    //TODO: send regen email + screen!
                } catch ( InvalidCredentialsException e) {
                    response = e.getMessage();
                    responseCode = 400;
                } catch (JsonParseException | IOException e) {
                    e.printStackTrace();
                    response = "La informació que has proporcionat conté caràcters no valids o no suportats. Si us plau, torna-ho a intentar.";
                    responseCode = 400;
                } catch (SQLException e) {
                    response = "El servei de registre no esta disponible  ara mateix, si us plau, torna-ho a provar en una estona. Disculpei les molèsties.";
                    e.printStackTrace();
                }
                if (responseCode >= 400) {
                    response = AuthView.generateIncorrectAuthUpView(templateEngine, response);
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
                    response = AuthView.generateCorrectSignUpResponse(templateEngine);
                    responseCode = 201;
                } catch (InvalidEmailException e) {
                    response = e.getMessage();
                    responseCode = 401;
                } catch ( InvalidPasswordException e) {
                    response = e.getMessage();
                    responseCode = 401;
                } catch (JsonParseException | IOException e) {
                    response = "La informació que has proporcionat conté caràcters no valids o no suportats. Si us plau, torna-ho a intentar.";
                    responseCode = 400;
                } catch (PSQLException e) {
                    response = "Ja existeix un altre usuari registrat amb aquesta adreça, si creus que algú  ha utilizat la teva adreça envia un correu a info.sosetseib@gmail.com.";
                    responseCode = 409;
                } catch (AddressException e) {
                    response = "El correu que has proporcionat no existeix si us plau, proporciona un correu vàlid.";
                    responseCode = 400;
                } catch (UserAlreadyRegisteredException e) {
                    response = e.getMessage();
                    responseCode = 409;
                } catch (Exception e) {
                    e.printStackTrace();
                    response = "El servei de registre no esta disponible  ara mateix, si us plau, torna-ho a provar en una estona. Disculpei les molèsties.";
                }

                if (responseCode >= 400) {
                    response = AuthView.generateIncorrectAuthUpView(templateEngine, response);
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
                //Send the logut page + delete sesssion id from the db
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/validation", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "";
                int responseCode = 500;
                String token = exchange.getRequestURI().getPath();
                String queryParams = exchange.getRequestURI().getQuery();
                String prefix = "/validation/";
                if (token.startsWith(prefix)) {
                    token = token.substring(prefix.length());
                }
                String userEmail = null;
                if (queryParams != null) {
                    Map<String, String> params = Arrays.stream(queryParams.split("&"))
                            .map(param -> param.split("=", 2))
                            .collect(Collectors.toMap(
                                    arr -> URLDecoder.decode(arr[0], StandardCharsets.UTF_8),
                                    arr -> arr.length > 1 ? URLDecoder.decode(arr[1], StandardCharsets.UTF_8) : ""
                            ));
                    userEmail = params.get("email");
                }
                try {
                    authService.validateRegisteredUser(token, userEmail);
                    exchange.getResponseHeaders().set("Location", "/sign-in");
                    exchange.sendResponseHeaders(302, -1);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response = "Fack";
                } catch (InvalidValidationTokenException e) {
                    response = "Token expried";
                    //TODO: Resend code
                }
                catch (Exception e) {
                    e.printStackTrace();
                    response  = "Fuark!";
                }
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }));

    }
}
