package controllers;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.sun.net.httpserver.HttpServer;

import entities.FileDao;
import exceptions.FileNotFoundException;
import services.FilesService;
import views.FilesView;

public class FilesController {

    private final FilesService filesService = new FilesService();

    public FilesController (HttpServer server) {
        server.createContext("/static/", new StaticFileHandler("./src/main/resources"));
        server.createContext("/files", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "";
                int responseCode = 500;
                String path = exchange.getRequestURI().getPath();
                String prefix = "/files";
                if (path.startsWith(prefix)) {
                    path = path.substring(prefix.length());
                }
                String[] route = path.split("/");
                route = Arrays.stream(route)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);
                try {
                    List<FileDao> files = filesService.getFilesFromRoute(route);
                    if (files.size() == 0) {
                        throw new FileNotFoundException();
                    }
                    response = FilesView.generateDirectoryView(Arrays.stream(route).toList(), files);
                } catch (SQLException e) {
                    response = "Internal server error please try again later";
                    System.out.println("Error with SQL query on registering user " + e.getMessage());
                } catch (FileNotFoundException e) {
                    response = "Return cute not found page";
                    responseCode = 404;
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
