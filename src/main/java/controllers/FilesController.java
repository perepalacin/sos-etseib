package controllers;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpServer;

import entities.FileDao;
import exceptions.FileNotFoundException;
import gg.jte.TemplateEngine;
import services.FilesService;
import views.FilesView;

public class FilesController {

    private final FilesService filesService = new FilesService();

    public FilesController (HttpServer server, TemplateEngine templateEngine) {
        server.createContext("/static/", new StaticFileHandler("./src/main/resources"));
        server.createContext("/files", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                HttpUtils.protectedRoutesMiddleware(exchange);
                String response = "";
                int responseCode = 500;
                String path = exchange.getRequestURI().getPath();
                String query = exchange.getRequestURI().getQuery();
                String prefix = "/files";
                if (path.startsWith(prefix)) {
                    path = path.substring(prefix.length());
                }
                List<String> route = Arrays.stream(path.split("/")).toList();
                route = route.stream()
                        .filter(s -> !s.isEmpty()).toList();

                String searchInput = null;
                if (query != null) {
                    Map<String, String> queryParams = Arrays.stream(query.split("&"))
                        .map(param -> param.split("=", 2))
                        .collect(Collectors.toMap(
                            arr -> URLDecoder.decode(arr[0], StandardCharsets.UTF_8),
                            arr -> arr.length > 1 ? URLDecoder.decode(arr[1], StandardCharsets.UTF_8) : ""
                    ));
                    searchInput = queryParams.get("search");
                }
                try {
                    List<FileDao> files = filesService.getFilesFromRoute(route, searchInput);
                    if (files.isEmpty()) {
                        //TODO: If last crumb is folder -> empty folder, else -> not found
                        throw new FileNotFoundException();
                    } else if (files.size() == 1 && Objects.equals(files.getFirst().getName(), route.getLast())){
                        System.out.println("FILE!");
                        String fileContents = filesService.showFileContent(files.getFirst().getName());
                        response = FilesView.generateFileView(templateEngine, fileContents, files.getFirst().getName());
                    } else {
                        response = FilesView.generateDirectoryView(templateEngine, route, files);
                    }
                    responseCode = 200;

                } catch (SQLException e) {
                    response = "Internal server error please try again later";
                    System.out.println("Error with SQL query on registering user " + e.getMessage());
                } catch (FileNotFoundException e) {
                    response = "Return cute not found page";
                    responseCode = 404; //TODO: is it a 404 or a 200?
                }
                exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                String respText = "You are not allowed to delete this file!";
                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
    }

}
