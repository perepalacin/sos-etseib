package repositories;

import entities.CommentDao;
import entities.FileLikesDao;
import exceptions.UnauthorizedRequestException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FeedbackRepository {

    public FeedbackRepository() {
        createLikesTable();
        createAggregatedLikesTable();
        createCommentsTable();
    }

    private void createLikesTable() {
        String createSqlTable = "CREATE TABLE IF NOT EXISTS likes ( " +
                "id SERIAL PRIMARY KEY, " +
                "file_id INTEGER REFERENCES files(id) ON DELETE CASCADE, " +
                "liked BOOLEAN NOT NULL DEFAULT True, " +
                "user_id UUID REFERENCES users(id) ON DELETE CASCADE," +
                "UNIQUE (user_id, file_id));";

        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(createSqlTable)) {
                statement.execute();
                connection.close();
            }
            System.out.println("Table 'likes' created or already exist.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize the likes table");
            e.printStackTrace();
        }
    }

    private void createAggregatedLikesTable() {
        String createSqlTable = "CREATE TABLE IF NOT EXISTS aggregated_likes (" +
                "id SERIAL PRIMARY KEY, " +
                "file_id INTEGER REFERENCES files(id) ON DELETE CASCADE, " +
                "likes INTEGER NOT NULL DEFAULT 0, " +
                "dislikes INTEGER NOT NULL DEFAULT 0);";

        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(createSqlTable)) {
                statement.execute();
                connection.close();
            }
            System.out.println("Table 'aggregated likes' created or already exist.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize the aggregated likes table");
            e.printStackTrace();
        }
    }

    private void createCommentsTable() {
        String createSqlTable = "CREATE TABLE IF NOT EXISTS comments ( " +
                "id SERIAL PRIMARY KEY, " +
                "file_id INTEGER REFERENCES files(id) ON DELETE CASCADE, " +
                "comment VARCHAR(255) NOT NULL, " +
                "parent_id INTEGER REFERENCES comments(id) ON DELETE CASCADE, " +
                "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "user_id UUID REFERENCES users(id) ON DELETE CASCADE);";

        try (Connection connection = DatabaseConnectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(createSqlTable)) {
                statement.execute();
                connection.close();
            }
            System.out.println("Table 'comments' created or already exist.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize the 'comments' table");
            e.printStackTrace();
        }
    }

    public String findIfUserLikedFile(UUID userId, int fileId) throws SQLException {
        String sql = "SELECT 1 FROM likes WHERE user_id = ? AND file_id = ? LIMIT 1;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, userId.toString());
        ps.setInt(2, fileId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ps.close();
            connection.close();
            return rs.getBoolean("liked") ? "liked" : "disliked";
        }
        rs.close();
        ps.close();
        connection.close();
        return null;
    }

    private FileLikesDao findFileLikesByFileId(int fileId) throws SQLException {
        String sql = "SELECT likes, dislikes FROM aggregated_likes WHERE file_id = ?;";
        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, fileId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ps.close();
            connection.close();
            return new FileLikesDao(
                    fileId,
                    rs.getInt("likes"),
                    rs.getInt("dislikes"),
                    false,
                    false
            );
        }

        rs.close();
        ps.close();
        connection.close();

        return null;
    }

    public FileLikesDao saveUserLike(UUID userId, int fileId, boolean isLiked) throws PSQLException, SQLException {
        String firstSQLQuery = "INSERT INTO likes (user_id, file_id, liked) VALUES (?, ?, ?);";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(firstSQLQuery);

        ps.setObject(1, userId);
        ps.setInt(2, fileId);
        ps.setBoolean(3, isLiked);

        ps.executeUpdate();

        FileLikesDao fileLikes = findFileLikesByFileId(fileId);

        if (fileLikes == null) {
            String secondSQLQuery = "INSERT INTO aggregated_likes (file_id, likes, dislikes) VALUES (?, ?, ?);";
            PreparedStatement ps2 = connection.prepareStatement(secondSQLQuery);
            ps2.setInt(1, fileId);
            ps2.setInt(2, isLiked ? 1 : 0);
            ps2.setInt(3, isLiked ? 0 : 1);
            ps2.executeUpdate();

            ps.close();
            ps2.close();
            connection.close();

            return new FileLikesDao(fileId, isLiked ? 1 : 0, isLiked ? 0 : 1, isLiked, !isLiked);
        } else {
            String secondSQLQuery = "UPDATE aggregated_likes SET likes = ?, dislikes = ? WHERE file_id = ?;";
            PreparedStatement ps2 = connection.prepareStatement(secondSQLQuery);
            ps2.setInt(1, isLiked ? fileLikes.getLikes() + 1 : fileLikes.getLikes());
            ps2.setInt(2, isLiked ? fileLikes.getDislikes() : fileLikes.getDislikes() + 1);
            ps2.setInt(3, fileId);
            ps2.executeUpdate();

            ps.close();
            ps2.close();
            connection.close();

            return new FileLikesDao(fileId, isLiked ? fileLikes.getLikes() + 1 : fileLikes.getLikes(), isLiked ? fileLikes.getDislikes() : fileLikes.getDislikes() + 1, isLiked, !isLiked);
        }
    }

    public FileLikesDao removeUserLike(UUID userId, int fileId, boolean isLiked) throws PSQLException, SQLException {
        String firstSQLQuery = "DELETE FROM likes WHERE file_id = ? AND user_id = ?";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(firstSQLQuery);

        ps.setInt(1, fileId);
        ps.setObject(2, userId);

        ps.executeUpdate();

        FileLikesDao fileLikes = findFileLikesByFileId(fileId);

        if (fileLikes == null) {
            ps.close();
            connection.close();
            throw new SQLException();
        } else {
            String secondSQLQuery = "UPDATE aggregated_likes SET likes = ?, dislikes = ? WHERE file_id = ?;";
            PreparedStatement ps2 = connection.prepareStatement(secondSQLQuery);
            ps2.setInt(1, isLiked ? fileLikes.getLikes() - 1 : fileLikes.getLikes());
            ps2.setInt(2, isLiked ? fileLikes.getDislikes() : fileLikes.getDislikes() - 1);
            ps2.setInt(3, fileId);
            ps2.executeUpdate();

            ps.close();
            ps2.close();
            connection.close();

            return new FileLikesDao(fileId, isLiked ? fileLikes.getLikes() -1 : fileLikes.getLikes(), isLiked ? fileLikes.getDislikes() : fileLikes.getDislikes() - 1, false, false);
        }
    }

    public FileLikesDao editUserLike(UUID userId, int fileId, boolean swapToLiked) throws PSQLException, SQLException {
        String firstSQLQuery = "UPDATE likes SET liked = ? WHERE file_id = ? AND user_id = ?";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(firstSQLQuery);

        ps.setBoolean(1, swapToLiked);
        ps.setInt(2, fileId);
        ps.setObject(3, userId);

        ps.executeUpdate();

        FileLikesDao fileLikes = findFileLikesByFileId(fileId);

        if (fileLikes == null) {
            ps.close();
            connection.close();
            throw new SQLException();
        } else {
            String secondSQLQuery = "UPDATE aggregated_likes SET likes = ?, dislikes = ? WHERE file_id = ?;";
            PreparedStatement ps2 = connection.prepareStatement(secondSQLQuery);
            ps2.setInt(1, swapToLiked ? fileLikes.getLikes() + 1 : fileLikes.getLikes() - 1);
            ps2.setInt(2, swapToLiked ? fileLikes.getDislikes() - 1 : fileLikes.getDislikes() + 1);
            ps2.setInt(3, fileId);
            ps2.executeUpdate();
            ps.close();
            ps2.close();
            connection.close();
            return new FileLikesDao(fileId, swapToLiked ? fileLikes.getLikes()  + 1 : fileLikes.getLikes() - 1, swapToLiked ? fileLikes.getDislikes() - 1 : fileLikes.getDislikes() + 1, true, false);
        }
    }

    public CommentDao addUserComment(UUID userId, int fileId, int parentCommentId, String comment) throws SQLException {
        String firstSQLQuery = "INSERT INTO comments (user_id, file_id, comment, parent_id) VALUES (?, ?, ?, ?) RETURNING id;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(firstSQLQuery);

        ps.setObject(1, userId);
        ps.setInt(2, fileId);
        ps.setString(3, comment);
        ps.setObject(4, parentCommentId != -1 ? parentCommentId : null);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            rs.close();
            ps.close();
            connection.close();
            return new CommentDao(
                    id,
                    parentCommentId,
                    comment,
                    "Tú",
                    fileId,
                    new Date(),
                    0
            );
        }
        rs.close();
        ps.close();
        connection.close();
        return null;
    }

    public void updateUserComment(UUID userId, String newComent, int commentId) throws SQLException, UnauthorizedRequestException {
        String firstSQLQuery = "INSERT INTO comments (comment) VALUES (?) WHERE id = ? AND user_id = ?;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(firstSQLQuery);

        ps.setString(1, newComent);
        ps.setInt(2, commentId);
        ps.setString(3, String.valueOf(userId));

        ResultSet rs = ps.executeQuery();
        ps.close();
        connection.close();
        if (!rs.next()) {
            rs.close();
            throw new UnauthorizedRequestException();
        }
        rs.close();
    }

    public void deleteUserComment(UUID userId, int commentId) throws SQLException, UnauthorizedRequestException {
        String sql = "DELETE comment WHERE id = ? AND user_id = ?";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, commentId);
        ps.setString(2, String.valueOf(userId));

        ResultSet rs = ps.executeQuery();
        ps.close();
        connection.close();
        if (!rs.next()) {
            rs.close();
            throw new UnauthorizedRequestException();
        }
    }

    public List<CommentDao> fetchComments(int fileId, int parentCommentId) throws SQLException {
        List<CommentDao> commentsDao = new ArrayList<>();
        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = null;
        String sql;

        if (parentCommentId == -1) {
            sql = "SELECT comments.*, users.email, (SELECT COUNT(*) FROM comments AS c2 WHERE c2.parent_id = comments.id) AS nested_comments FROM comments LEFT JOIN users ON users.id = comments.user_id WHERE file_id = ? AND parent_id IS NULL;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, fileId);
        } else {
            sql = "SELECT comments.*, users.email FROM comments LEFT JOIN users ON users.id = comments.user_id WHERE file_id = ? AND parent_id = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, fileId);
            ps.setInt(2, parentCommentId);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String commentContent = rs.getString("comment");
            String userEmail = rs.getString("email");
            Date createdAt = rs.getDate("created_at");
            int nestedComments = 0;
            if (parentCommentId == -1) {
                nestedComments = rs.getInt("nested_comments");
            }
            CommentDao comment = new CommentDao(id, parentCommentId, commentContent, userEmail, fileId, createdAt, nestedComments);
            commentsDao.add(comment);
        }
        rs.close();
        ps.close();
        connection.close();

        return commentsDao;
    }
}
