package services;

import entities.FileDao;
import repositories.FilesRepository;

import java.sql.SQLException;
import java.util.List;

public class FilesService {

    private FilesRepository filesRepository = new FilesRepository();

    public List<FileDao> getFilesFromRoute(String[] route) throws SQLException {
        return filesRepository.getFilesFromRoute(route);

        //If the result is a file, get the contents from the s3 bucket and fetch the comments
        // else return the view
    }
}
