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
    public int id;
    public int parent_id;
    public String name;
    public String type;
    public String s3_url;
    public Date createdAt;
    public String sharedBy;
    public boolean userLiked;
    public boolean userDisliked;
    public int likes;
    public int dislikes;
    public int commentsCount;

    public String getFileExtension() {
        return Arrays.stream(this.getName().split("\\.")).toList().getLast().toLowerCase();
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
        System.out.println(extension);
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

