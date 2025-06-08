package my.kukish;

import com.sun.net.httpserver.HttpServer;
import my.kukish.handler.DownloadHandler;
import my.kukish.handler.StaticFileHandler;
import my.kukish.handler.UploadHandler;
import my.kukish.service.FileStorageService;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

public class FileServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        Files.createDirectories(Paths.get("uploads"));

        FileStorageService storageService = new FileStorageService("uploads");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticFileHandler("src/main/resources/index.html"));
        server.createContext("/upload", new UploadHandler(storageService));
        server.createContext("/download", new DownloadHandler(storageService));

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Сервер запущен на http://localhost:" + port);
    }
}

