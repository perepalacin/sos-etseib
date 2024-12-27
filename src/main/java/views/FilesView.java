package views;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.BreadcrumbsDao;
import entities.FileDao;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;

public class FilesView {

    public static String generateDirectoryView(TemplateEngine templateEngine, List<String> route, List<FileDao> files) {
        try {
            BreadcrumbsDao breadcrumbs = new BreadcrumbsDao(route);
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("title", "SOS - ETSEIB: Q2");
            params.put("breadcrumbs", breadcrumbs.getItems());
            params.put("tabName", "SOS - ETSEIB: Folder1");
            params.put("isUserLogged", true);
            params.put("files", files);

            templateEngine.render("pages/files.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: return 500 page
            return "Failed to compute";
        }
    }

    public static String generateFileView(TemplateEngine templateEngine, byte[] fileContent, FileDao file) {
        try {
            TemplateOutput output = new StringOutput();
            Map<String, Object> params = new HashMap<>();
            params.put("fileName", file.getName());
            String fileType;
            String programmingLanguage;
            if (file.isTextFile() || file.isCodeFile()) {
                String content = new String(fileContent, StandardCharsets.UTF_8);
                if (file.isTextFile()) {
                    content = HtmlUtils.escapeHtml(content);
                    fileType = "text";
                } else {
                    fileType = "code";
                    programmingLanguage = switch (file.getFileExtension().toLowerCase()) {
                        case "js", "javascript" -> "javascript";
                        case "ts" -> "typescript";
                        case "py" -> "python";
                        case "m" -> "matlab";
                        case "cpp" -> "cpp";
                        case "c" -> "c";
                        case "java" -> "java";
                        case "cs" -> "csharp";
                        case "ino", "arduino" -> "arduino";
                        default -> "plaintext"; // Default case if no known extension is matched
                    };
                    params.put("programmingLanguage", programmingLanguage);
                    System.out.println(programmingLanguage);
                }
                params.put("content", content);
            } else if (file.isImageFile()) {
                fileType = "image";
                String base64Content = Base64.getEncoder().encodeToString(fileContent);
                params.put("base64Content", base64Content);
                params.put("mimeType", file.getMimeType());
            } else if (file.isPdfFile()) {
                fileType = "pdf";
                String base64Content = Base64.getEncoder().encodeToString(fileContent);
                params.put("base64Content", base64Content);
            } else {
                fileType = "other";
            }

            params.put("fileType", fileType);
            templateEngine.render("pages/file-content.jte", params, output);

            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Return a 500 error page
            return "Failed to compute";
        }
    }
}
