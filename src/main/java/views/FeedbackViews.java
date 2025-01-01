package views;

import entities.CommentDao;
import entities.FileLikesDao;
import entities.dto.CommentDto;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackViews {

    public static String generateUpdatedLikesView(TemplateEngine templateEngine, FileLikesDao fileLikesDao) {
        try {
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("liked", fileLikesDao.isUserLikedIt());
            params.put("disliked", fileLikesDao.isUserDislikedIt());
            params.put("likes", fileLikesDao.getLikes());
            params.put("dislikes", fileLikesDao.getDislikes());
            params.put("comments", 12);

            if (fileLikesDao.isUserLikedIt()) {
                params.put("likeApiRoute", "/api/v1/feedback?action=unlike&fileId=" + String.valueOf(fileLikesDao.getFileId()));
                params.put("dislikeApiRoute", "/api/v1/feedback?action=swapToDislike&fileId=" + String.valueOf(fileLikesDao.getFileId()));
            } else if (fileLikesDao.isUserDislikedIt()) {
                params.put("likeApiRoute", "/api/v1/feedback?action=swapToLike&fileId=" + String.valueOf(fileLikesDao.getFileId()));
                params.put("dislikeApiRoute", "/api/v1/feedback?action=undislike&fileId=" + String.valueOf(fileLikesDao.getFileId()));
            } else {
                params.put("likeApiRoute", "/api/v1/feedback?action=like&fileId=" + String.valueOf(fileLikesDao.getFileId()));
                params.put("dislikeApiRoute", "/api/v1/feedback?action=dislike&fileId=" + String.valueOf(fileLikesDao.getFileId()));
            }

            templateEngine.render("responses/feedback/likes-dislikes-buttons.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: return 500 page
            return "Failed to compute";
        }
    }

    public static String generateRootFileCommentsResponse(TemplateEngine templateEngine, List<CommentDao> comments, int fileId, int parentCommentId) {
        try {
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("comments", comments);
            params.put("fileId", fileId);
            params.put("parentCommentId", parentCommentId);

            templateEngine.render("responses/feedback/root-file-comments.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: return 500 page
            return "Failed to compute";
        }
    }

    public static String generateNewCommentView(TemplateEngine templateEngine, CommentDao comment, int fileId, boolean isNestedComment) {
        try {
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("comment", comment);
            params.put("fileId", fileId);
            params.put("isNestedComment", isNestedComment);

            templateEngine.render("responses/feedback/new-comment.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: return 500 page
            return "Failed to compute";
        }
    }

}
