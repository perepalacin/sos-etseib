package services;

import entities.FileDao;
import exceptions.FileNotFoundException;
import repositories.FilesRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesService {

    private FilesRepository filesRepository = new FilesRepository();

    public List<FileDao> getFilesFromRoute(List<String> route, String searchInput) throws SQLException, FileNotFoundException {
        List<FileDao> files;
        System.out.println("Search input" + searchInput);
        if (searchInput != null) {
            files = filesRepository.getFilesNestedInsideRoute(route, searchInput);
        } else {
            files = filesRepository.getFilesFromRoute(route);
        }
        if (files.isEmpty()) {
            throw new FileNotFoundException();
        } else if (files.size() == 1 && Objects.equals(files.getFirst().getName(), route.getLast())){
            System.out.println("FILE!");
            return files;
        }
        return files;
        //If the result is a file, get the contents from the s3 bucket and fetch the comments
        // else return the view
    }
}
