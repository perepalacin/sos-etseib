package entities;

import annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class CommentDao {
    public int id;
    public int parentCommentId;
    public String commentContent;
    public String userEmail;
    public int fileId;
    public Date createdAt;
    public int nestedComments;
}
