package services;

import entities.CommentDao;
import entities.FileLikesDao;
import entities.dto.CommentDto;
import exceptions.BadRequestException;
import exceptions.UnauthorizedRequestException;
import repositories.FeedbackRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class FeedbackService {
    private FeedbackRepository feedbackRepository = new FeedbackRepository();

    public FileLikesDao manageFileLike(UUID userId, int fileId, String action) throws SQLException, BadRequestException {
        //String action can take 6 values, "like", "dislike", "unlike", "undislike", "swapToLike", "swapToDislike"
        switch (action) {
            case "like" -> {
                return feedbackRepository.saveUserLike(userId, fileId, true);
            }
            case "dislike" -> {
                return feedbackRepository.saveUserLike(userId, fileId, false);
            }
            case "unlike" -> {
                return feedbackRepository.removeUserLike(userId, fileId, true);
            }
            case "undislike" -> {
                return feedbackRepository.removeUserLike(userId, fileId, false);
            }
            case "swapToLike" -> {
                return feedbackRepository.editUserLike(userId, fileId, true);
            }
            case "swapToDislike" -> {
                return feedbackRepository.editUserLike(userId, fileId, false);
            }
            default -> throw new BadRequestException("La URL emparada està formatada malament, si us plau utilitza ?action=string&fileId=int");
        }
    }

    public List<CommentDao> fetchFileComments(int fileId, int parentCommentId) throws SQLException {
        return feedbackRepository.fetchComments(fileId, parentCommentId);
    }

    public CommentDao addCommentToFile(UUID userId, int fileId, int parentCommentId, String comment) throws SQLException {
        return feedbackRepository.addUserComment(userId, fileId, parentCommentId, comment);
    }

    public void updateFileComment(UUID userId, String newComment, int commentToEditId) throws SQLException, UnauthorizedRequestException {
        feedbackRepository.updateUserComment(userId, newComment, commentToEditId);
    }

    public void deleteFileComment(UUID userId, int commentToEditId) throws SQLException, UnauthorizedRequestException {
        feedbackRepository.deleteUserComment(userId, commentToEditId);
    }

}
