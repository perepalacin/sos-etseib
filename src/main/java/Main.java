import com.sun.net.httpserver.HttpServer;
import controllers.AuthController;
import controllers.FilesController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import repositories.CloudflareR2Client;

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
        CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte")); // This is the directory where your .jte files are located.
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);

        server.setExecutor(null);
        server.start();
        new FilesController(server, templateEngine);
        new AuthController(server, templateEngine);
    }
}
