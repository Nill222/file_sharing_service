package my.kukish.util;

import java.util.*;
import java.nio.charset.StandardCharsets;

public class MultipartParser {
    public static Map<String, Object> parse(byte[] body, String contentType) {
        Map<String, Object> result = new HashMap<>();
        String boundary = "--" + contentType.split("boundary=")[1];
        String[] parts = new String(body, StandardCharsets.UTF_8).split(boundary);

        for (String part : parts) {
            if (part.contains("filename=\"")) {
                int start = part.indexOf("filename=\"") + 10;
                int end = part.indexOf("\"", start);
                String filename = part.substring(start, end);

                int fileStart = part.indexOf("\r\n\r\n") + 4;
                byte[] fileContent = Arrays.copyOfRange(body, body.length - (part.length() - fileStart), body.length - 2);
                result.put("filename", filename);
                result.put("file", fileContent);
            }
        }

        return result;
    }
}
