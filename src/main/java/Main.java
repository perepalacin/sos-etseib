import com.sun.net.httpserver.HttpServer;
import controllers.AuthController;
import controllers.FilesController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {


    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        System.out.println("App initialized correctly in port " + serverPort);
        server.createContext("/ping", (exchange -> {
            String respText = "pong!";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        server.setExecutor(null);
        server.start();
        new FilesController(server);
        new AuthController(server);
    }
}
