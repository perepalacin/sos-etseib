package entities;

import java.util.Arrays;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDao {
    int id;
    int parent_id;
    String name;
    String type;
    String s3_url;
    Date createdAt;
    String sharedBy;

    public String getFileExtension() {
        return Arrays.stream(this.getName().split("\\.")).toList().getLast();
    }

    public boolean isTextFile() {
        String extension = this.getFileExtension();
        return Arrays.asList("txt").contains(extension);
    }

    public boolean isCodeFile() {
        String extension = this.getFileExtension();
        return Arrays.asList("java", "py", "js", "html", "css", "json", "xml", "m").contains(extension);
    }

    public boolean isImageFile() {
        String extension = this.getFileExtension();
        return Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp").contains(extension);
    }

    public boolean isPdfFile() {
        String extension = this.getFileExtension();
        return "pdf".equals(extension);
    }

    public String getMimeType() {
        String extension = this.getFileExtension();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            case "webp" -> "image/webp";
            case "pdf" -> "application/pdf";
            default -> "application/octet-stream";
        };
    }
}

