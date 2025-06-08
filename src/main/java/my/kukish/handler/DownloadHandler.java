package my.kukish.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import my.kukish.entity.FileMetadata;
import my.kukish.service.FileStorageService;


import java.io.IOException;
import java.nio.file.Files;

public class DownloadHandler implements HttpHandler {
    private final FileStorageService storageService;

    public DownloadHandler(FileStorageService service) {
        this.storageService = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String uuid = path.substring(path.lastIndexOf('/') + 1);

        FileMetadata metadata = storageService.getFile(uuid);
        if (metadata == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        byte[] file = Files.readAllBytes(metadata.getPath());
        exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=\"" + metadata.getPath().getFileName() + "\"");
        exchange.sendResponseHeaders(200, file.length);
        exchange.getResponseBody().write(file);
        exchange.close();
    }
}