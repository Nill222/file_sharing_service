package my.kukish.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticFileHandler implements HttpHandler {

    private final Path filePath;

    public StaticFileHandler(String path) {
        this.filePath = Path.of(path);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!Files.exists(filePath)) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        byte[] bytes = Files.readAllBytes(filePath);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}

