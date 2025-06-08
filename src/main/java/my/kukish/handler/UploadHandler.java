package my.kukish.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import my.kukish.service.FileStorageService;
import my.kukish.util.MultipartParser;

import java.io.IOException;
import java.util.Map;

public class UploadHandler implements HttpHandler {
    private final FileStorageService storageService;

    public UploadHandler(FileStorageService service) {
        this.storageService = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        byte[] body = exchange.getRequestBody().readAllBytes();
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");

        Map<String, Object> parsed = MultipartParser.parse(body, contentType);
        String filename = (String) parsed.get("filename");
        byte[] fileContent = (byte[]) parsed.get("file");

        var metadata = storageService.saveFile(filename, fileContent);
        String json = "{\"uuid\":\"" + metadata.getUuid() + "\"}";

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.length());
        exchange.getResponseBody().write(json.getBytes());
        exchange.close();
    }
}

