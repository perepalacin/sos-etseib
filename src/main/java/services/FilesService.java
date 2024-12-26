package services;

import entities.FileDao;
import exceptions.FileNotFoundException;
import repositories.CloudflareR2Client;
import repositories.FilesRepository;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

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

    public String getFileContent(String fileName) {
        try {
            return r2Client.getObjectContent(fileName);
        } catch (Exception e) {
            return "error ";
        }
    }

    public byte[] getObjectBytes(String fileName) {
        try {
            return r2Client.getObjectContentAsBytes(fileName);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return null;
        }
    }


}
