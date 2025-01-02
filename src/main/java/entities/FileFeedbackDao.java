package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class FileFeedbackDao {
    public int fileId;
    public int likes;
    public int dislikes;
    public int commentsCount;
    public boolean userLikedIt;
    public boolean userDislikedIt;
}
