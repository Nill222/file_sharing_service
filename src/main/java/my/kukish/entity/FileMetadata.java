package my.kukish.entity;

import java.nio.file.Path;

public class FileMetadata {
    private final String uuid;
    private final Path path;
    private long lastDownloaded;

    public FileMetadata(String uuid, Path path) {
        this.uuid = uuid;
        this.path = path;
        this.lastDownloaded = System.currentTimeMillis();
    }

    public String getUuid() { return uuid; }
    public Path getPath() { return path; }
    public long getLastDownloaded() { return lastDownloaded; }
    public void updateDownloadTime() { this.lastDownloaded = System.currentTimeMillis(); }
}

