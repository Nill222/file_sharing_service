package my.kukish.service;

import my.kukish.entity.FileMetadata;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FileStorageService {
    private final Path uploadDir;
    private final Map<String, FileMetadata> fileMap = new ConcurrentHashMap<>();

    public FileStorageService(String dir) {
        this.uploadDir = Paths.get(dir);
    }

    public FileMetadata saveFile(String filename, byte[] content) throws IOException {
        String uuid = UUID.randomUUID().toString();
        Path fullPath = uploadDir.resolve(uuid + "_" + filename);
        Files.write(fullPath, content);
        FileMetadata metadata = new FileMetadata(uuid, fullPath);
        fileMap.put(uuid, metadata);
        return metadata;
    }

    public FileMetadata getFile(String uuid) {
        FileMetadata meta = fileMap.get(uuid);
        if (meta != null) {
            meta.updateDownloadTime();
        }
        return meta;
    }
}
