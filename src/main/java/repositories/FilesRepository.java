package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.FileDao;

public class FilesRepository {

    public FilesRepository() {
        createTable();
    }

    private void createTable() {
        String createSqlTable = "CREATE TABLE IF NOT EXISTS files (" +
                "id SERIAL PRIMARY KEY, " +
                "parent_id INTEGER REFERENCES files(id) ON DELETE CASCADE, " +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL CHECK (type IN ('file', 'folder')), " +
                "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "shared_by TEXT, " + 
                "s3_url TEXT);";

        String createParentIdIndexSql = "CREATE INDEX IF NOT EXISTS idx_files_parent_id ON files(parent_id);";
            
        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(createSqlTable)) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement(createParentIdIndexSql)) {
                statement.execute();
            }
            System.out.println("Table 'files' and indexes created or already exist.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize the files table and indexes");
            e.printStackTrace();
        }
    }

    public List<FileDao> getFilesFromRoute(List<String> routes) throws SQLException {
        String sqlQuery = "WITH RECURSIVE " +
                "path_components AS (" +
                "    SELECT ?::text[] AS path_arr" +
                ")," +
                "path_to_item AS ( " +
                "    SELECT " +
                "        i.id, " +
                "        i.parent_id, " +
                "        i.name, " +
                "        i.type, " +
                "        i.s3_url, " +
                "        i.created_at, " +
                "        i.shared_by, " +
                "        1 AS level " +
                "    FROM files i " +
                "    CROSS JOIN path_components pc " +
                "    WHERE i.parent_id IS NULL AND i.name = pc.path_arr[1] " +
                "     " +
                "    UNION ALL " +
                " " +
                "    SELECT " +
                "        i.id, " +
                "        i.parent_id, " +
                "        i.name, " +
                "        i.type, " +
                "        i.s3_url, " +
                "        i.created_at, " +
                "        i.shared_by, " +
                "        pti.level + 1 AS level " +
                "    FROM files i " +
                "    INNER JOIN path_to_item pti ON i.parent_id = pti.id " +
                "    CROSS JOIN path_components pc " +
                "    WHERE i.name = pc.path_arr[(pti.level + 1)] " +
                "      AND (pti.level + 1) <= array_length(pc.path_arr, 1) " +
                "), " +
                "target_item AS ( " +
                "    SELECT pti.* " +
                "    FROM path_to_item pti " +
                "    CROSS JOIN path_components pc " +
                "    WHERE pti.level = array_length(pc.path_arr, 1) " +
                "), " +
                "result AS ( " +
                "    SELECT " +
                "        t.id, " +
                "        t.parent_id, " +
                "        t.name, " +
                "        t.type, " +
                "        t.s3_url, " +
                "        t.created_at, " +
                "        t.shared_by, " +
                "        t.level " +
                "    FROM target_item t " +
                "    WHERE t.type = 'file' " +
                " " +
                "    UNION ALL " +
                " " +
                "    SELECT " +
                "        i.id, " +
                "        i.parent_id, " +
                "        i.name, " +
                "        i.type, " +
                "        i.s3_url, " +
                "        i.created_at, " +
                "        i.shared_by, " +
                "        t.level + 1 AS level " +
                "    FROM files i " +
                "    JOIN target_item t ON i.parent_id = t.id " +
                "    WHERE t.type = 'folder' " +
                ") " +
                "SELECT * " +
                "FROM result;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sqlQuery);

        java.sql.Array sqlArray = connection.createArrayOf("text", routes.toArray());
        ps.setArray(1, sqlArray);

        List<FileDao> files = new ArrayList<>();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Integer parentId = rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null;
            String name = rs.getString("name");
            String type = rs.getString("type");
            String s3Url = rs.getString("s3_url");
            Date createdAt = rs.getDate("created_at");
            String sharedBy = rs.getString("shared_by");

            FileDao file = new FileDao(id, parentId, name, type, s3Url, createdAt, sharedBy);
            files.add(file);
        }
        rs.close();
        ps.close();
        connection.close();

        return files;
    }

    public List<FileDao> getFilesNestedInsideRoute(List<String> routes, String searchInput) throws SQLException {
        String sqlQuery =
                "WITH RECURSIVE path_components AS ( " +
                        "    SELECT ?::text[] AS path_arr" +
                        "), " +
                        "path_to_folder AS (" +
                        "    SELECT f.*, 1 AS level" +
                        "    FROM files f, path_components pc" +
                        "    WHERE f.parent_id IS NULL AND f.name = pc.path_arr[1]" +
                        "  UNION ALL" +
                        "    SELECT f2.*, p.level + 1 AS level" +
                        "    FROM files f2" +
                        "    INNER JOIN path_to_folder p ON f2.parent_id = p.id, path_components pc" +
                        "    WHERE f2.name = pc.path_arr[p.level + 1]" +
                        "      AND p.level + 1 <= array_length(pc.path_arr, 1)" +
                        ")," +
                        "recursive_files AS (" +
                        "    SELECT * FROM files WHERE id = (SELECT id FROM path_to_folder ORDER BY level DESC LIMIT 1)" +
                        "  UNION ALL" +
                        "    SELECT f.* FROM files f INNER JOIN recursive_files rf ON f.parent_id = rf.id" +
                        ")" +
                        "SELECT * FROM recursive_files WHERE name ILIKE '%' || ? || '%' and type = 'file';";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sqlQuery);

        java.sql.Array sqlArray = connection.createArrayOf("text", routes.toArray());
        ps.setArray(1, sqlArray);
        ps.setString(2, searchInput);

        List<FileDao> files = new ArrayList<>();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Integer parentId = rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null;
            String name = rs.getString("name");
            String type = rs.getString("type");
            String s3Url = rs.getString("s3_url");
            Date createdAt = rs.getDate("created_at");
            String sharedBy = rs.getString("shared_by");

            FileDao file = new FileDao(id, parentId, name, type, s3Url, createdAt, sharedBy);
            files.add(file);
        }
        rs.close();
        ps.close();
        connection.close();

        return files;
    }

}
