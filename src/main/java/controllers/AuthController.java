package controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sun.net.httpserver.Headers;
import exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
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
import views.AuthView;

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
                String userEmail = "";
                try {
                    UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
                    UserDao userDao = new UserDao(user.getEmail(), user.getPassword());
                    userEmail = userDao.getEmail();
                    UUID sessionId = authService.signInUser(userDao);

                    String cookie = "session_id=" + sessionId.toString() + "; HttpOnly; Path=/";
                    exchange.getResponseHeaders().add("Set-Cookie", cookie);

                    exchange.getResponseHeaders().set("Location", "/files/root");
                    exchange.sendResponseHeaders(302, -1);
                } catch (UserNotActivatedException e) {
                    try {
                        authService.resendValidationEmail(userEmail);
                        response = AuthView.generateUserPendingActivation(templateEngine, userEmail);
                        responseCode = 403;
                    } catch (MessagingException ex) {
                        response = "El correu que has proporcionat no existeix. Si us plau, proporciona un correu vàlid.";
                        responseCode = 400;
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (InvalidCredentialsException e) {
                    response = e.getMessage();
                    responseCode = 400;
                } catch (JsonParseException | IOException e) {
                    e.printStackTrace();
                    response = "La informació que has proporcionat conté caràcters no vàlids o no suportats. Si us plau, torna-ho a intentar.";
                    responseCode = 400;
                } catch (Exception e) {
                    response = "El servei de registre no està disponible ara mateix. Si us plau, torna-ho a provar en una estona. Disculpeu les molèsties.";
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
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
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
                    response = AuthView.generateCorrectSignUpResponse(templateEngine, newUser.getEmail());
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
                String response;
                Map<String, String> cookiesMap = HttpUtils.parseCookies(exchange.getRequestHeaders());
                String sessionId = cookiesMap.get("session_id");

                boolean isLoggedOut = false;
                if (sessionId != null) {
                    try {
                        isLoggedOut = authService.logOutUser(UUID.fromString(sessionId));
                    } catch (SQLException e) {
                        response = "El servei per tancar les sessions no està disponible actualment. Prova-ho més tard.";
                        exchange.sendResponseHeaders(500, response.getBytes().length);
                    }
                }

                if (isLoggedOut) {
                    exchange.getResponseHeaders().add("Set-Cookie", "session_id=; HttpOnly; Max-Age=0; Path=/");
                    exchange.getResponseHeaders().set("Location", "/sign-in");
                    response = "La teva sessió ha sigut tancada correctament.";
                    exchange.sendResponseHeaders(302, -1);
                } else {
                    response = "Necessites haver iniciar sessió primer per poder tancar la sessió.";
                    exchange.sendResponseHeaders(401, response.getBytes().length);
                }
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/validation", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "";
                int responseCode = 500;
                boolean allowResendLink = false;
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
                } catch (InvalidValidationTokenException e) {
                    response = e.getMessage();
                    responseCode = 400;
                } catch (ExpiredJwtException e) {
                    response = "L'enllaç d'activació ha caducat, t'acabem d'enviar un de nou. Si us plau, revisa el correu i la carpeta de spam.";
                    responseCode = 410;
                    allowResendLink = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    response = "El servei per activar el teu usuari no està disponible ara mateix. Si us plau, intenta-ho més tard o envia un correu a info.sosetseib@gmail.com";
                }
                if (responseCode >= 400) {
                    response = AuthView.generateUserValidationErrorPage(templateEngine, response, allowResendLink, userEmail);
                }
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }));

        server.createContext("/api/v1/auth/validation", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "";
                int responseCode = 500;
                String queryParams = exchange.getRequestURI().getQuery();
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
                    authService.resendValidationEmail(userEmail);
                    responseCode = 200;
                    response = AuthView.generateValidResendActivationEmail(templateEngine, userEmail);
                } catch (MessagingException e) {
                    response = AuthView.generateIncorrectAuthUpView(templateEngine, "El correu que has proporcionat no existeix si us plau, proporciona un correu vàlid.");
                    responseCode = 400;
                } catch (SQLException e) {
                    response = AuthView.generateIncorrectAuthUpView(templateEngine, "Hi ha un problema amb el servei per reenviar el correu de validació. Si us plau, intenta-ho més tard o envia un correu a info.sosetseib@gmail.com");
                    responseCode = 500;
                }
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

    }
}
