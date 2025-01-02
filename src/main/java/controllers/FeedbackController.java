package controllers;

import com.sun.net.httpserver.HttpServer;
import entities.CommentDao;
import entities.dto.CommentDto;
import exceptions.BadRequestException;
import exceptions.UnauthorizedRequestException;
import gg.jte.TemplateEngine;
import services.BodyParser;
import services.FeedbackService;
import views.FeedbackViews;
import views.FilesView;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class FeedbackController {

    private FeedbackService feedbackService = new FeedbackService();

    public FeedbackController (HttpServer server, TemplateEngine templateEngine) {
        server.createContext("/api/v1/feedback", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                UUID userId = HttpUtils.protectedRoutesMiddleware(exchange);
                String response = "";
                int responseCode = 500;
                String query = exchange.getRequestURI().getQuery();
                String action = null;
                int fileId = -1;
                int parentCommentId = -1;
                if (query != null) {
                    Map<String, String> queryParams = Arrays.stream(query.split("&"))
                            .map(param -> param.split("=", 2))
                            .collect(Collectors.toMap(
                                    arr -> URLDecoder.decode(arr[0], StandardCharsets.UTF_8),
                                    arr -> arr.length > 1 ? URLDecoder.decode(arr[1], StandardCharsets.UTF_8) : ""
                            ));
                    if (queryParams.containsKey("action")) {
                        action = queryParams.get("action");
                    }
                    if (queryParams.containsKey("parentCommentId")) {
                        parentCommentId = Integer.parseInt(queryParams.get("parentCommentId"));
                    }
                    fileId = Integer.parseInt(queryParams.get("fileId"));
                }
                if (fileId == -1) {
                    responseCode = 404;
                    response = "File not found";
                }
                try {
                    if (action != null) {
                        //TODO: manage likes!
//                        FileFeedbackDao filesLikesDao = feedbackService.manageFileLike(userId, fileId, action);
//                        response = FeedbackViews.generateUpdatedLikesView(templateEngine, filesLikesDao);
                    } else {
                        CommentDto commentDto = BodyParser.parseRequestBody(exchange, CommentDto.class);
                        if (commentDto != null && commentDto.getCommentContent() != "") {
                            commentDto.setParentCommentId(parentCommentId);
                            commentDto.setFileId(fileId);
                            CommentDao createdComment = feedbackService.addCommentToFile(userId, fileId, parentCommentId, commentDto.getCommentContent());
                            response = FeedbackViews.generateNewCommentView(templateEngine, createdComment, fileId, parentCommentId != -1);
                        } else {
                            throw new BadRequestException("No pots afegir comentaris sense text.");
                        }
                    }
                    responseCode = 200;
                } catch (UnauthorizedRequestException e) {
                    response = e.getMessage();
                    responseCode = 403;
                } catch (SQLException e) {
                    e.printStackTrace();
                    response = "Internal server error please try again later";
                    System.out.println(e.getMessage());
                } catch (BadRequestException e) {
                    response = e.getMessage();
                    responseCode = 400;
                } catch (Exception e) {
                    e.printStackTrace();
                    response = FilesView.generate404FileView(templateEngine);
                    responseCode = 404;
                }
                exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
            } else if ("GET".equals(exchange.getRequestMethod())) {
                UUID userId = HttpUtils.protectedRoutesMiddleware(exchange);
                String response = "";
                int responseCode = 500;
                int fileId = -1;
                int parentCommentId = -1;
                String query = exchange.getRequestURI().getQuery();
                if (query != null) {
                    Map<String, String> queryParams = Arrays.stream(query.split("&"))
                            .map(param -> param.split("=", 2))
                            .collect(Collectors.toMap(
                                    arr -> URLDecoder.decode(arr[0], StandardCharsets.UTF_8),
                                    arr -> arr.length > 1 ? URLDecoder.decode(arr[1], StandardCharsets.UTF_8) : ""
                            ));
                    if (queryParams.containsKey("parentCommentId")) {
                        parentCommentId = Integer.parseInt(queryParams.get("parentCommentId"));
                    }
                    fileId = Integer.parseInt(queryParams.get("fileId"));
                }
                if (fileId == -1) {
                    responseCode = 404;
                    response = "File not found";
                }
                try {
                    List<CommentDao> comments = feedbackService.fetchFileComments(fileId, parentCommentId);
                    response = FeedbackViews.generateRootFileCommentsResponse(templateEngine,comments, fileId, parentCommentId);
                    responseCode = 200;
                } catch (UnauthorizedRequestException e) {
                    response = e.getMessage();
                    responseCode = 403;
                } catch (SQLException e) {
                    response = "Internal server error please try again later";
                    System.out.println("Error with SQL query on registering user " + e.getMessage());
                } catch (Exception e) {
                    response = FilesView.generate404FileView(templateEngine);
                    responseCode = 404;
                }
                exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
    }

}
