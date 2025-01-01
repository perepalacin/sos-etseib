package entities.dto;

import annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDto {
    public int id;
    public int fileId;
    public int parentCommentId;
    @Required
    public String commentContent;

    public CommentDto(String commentContent) {
        this.commentContent = commentContent;
    }
}
