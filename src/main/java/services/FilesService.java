package services;

import entities.FileDao;
import exceptions.FileNotFoundException;
import repositories.CloudflareR2Client;
import repositories.FilesRepository;

import java.sql.SQLException;
import java.util.*;

public class FilesService {

    private FilesRepository filesRepository = new FilesRepository();
    CloudflareR2Client.S3Config config = new CloudflareR2Client.S3Config();
    CloudflareR2Client r2Client = new CloudflareR2Client(config);

    public List<FileDao> getFilesFromRoute(List<String> route, String searchInput) throws SQLException, FileNotFoundException {
        List<FileDao> files;

        if (searchInput != null) {
            files = filesRepository.getFilesNestedInsideRoute(route, searchInput);
        } else {
            files = filesRepository.getFilesFromRoute(route);
        }
        return files;
//        if (files.isEmpty()) {
//            //TODO: If last crumb is folder -> empty folder, else -> not found
//            throw new FileNotFoundException();
//        } else if (files.size() == 1 && Objects.equals(files.getFirst().getName(), route.getLast())){
//            System.out.println("FILE!");
//            return files;
//        }
//        return files;
        //If the result is a file, get the contents from the s3 bucket and fetch the comments
        // else return the view
    }

    public String showFileContent(String fileKey) {
        try {
            String content = r2Client.getObjectContent(fileKey);

            Map<String, String> params = new HashMap<>();
            params.put("fileName", fileKey);
            params.put("content", content);

            // Render the JTE template with the parameters
//            return TemplateRenderer.render("FileContent.jte", params);
            return content;
        } catch (Exception e) {
            // Handle exceptions and render an error page
            Map<String, Object> params = new HashMap<>();
            params.put("errorMessage", "Failed to load file: " + e.getMessage());
            return "error";
//            return TemplateRenderer.render("Error.jte", params);
        }
    }
}
