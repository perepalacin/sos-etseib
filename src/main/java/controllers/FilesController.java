package controllers;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;

public class FilesController {

    public FilesController (HttpServer server) {
        server.createContext("/api/v1/files", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String respText = "<p>Here is your file my man!</p>";
                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
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
