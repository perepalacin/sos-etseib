package views;

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

    public static String generateFileView(TemplateEngine templateEngine, String fileContent, String fileName) {
        try {
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("fileName", fileName);
            params.put("content", fileContent);

            templateEngine.render("pages/file-content.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: return 500 page
            return "Failed to compute";
        }
    }
}
