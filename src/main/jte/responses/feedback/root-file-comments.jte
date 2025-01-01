@param int fileId
@param java.util.List<entities.CommentDao> comments
@param int parentCommentId

@if(parentCommentId == -1)
    <div class="w-100 flex flex-column gap-0-5">
        <h2 style="margin-bottom: 0.5rem">Comentaris i preguntes</h2>
        <form hx-post="/api/v1/feedback?fileId=${fileId}" hx-trigger="submit" hx-target="#new-comments-container" hx-swap="outerHTML" hx-target-error="#toast" class="flex flex-column gap-0-5 align-end mb-1" >
            <textarea type="text" name="commentContent" maxlength="250" class="w-100 comment-input" placeholder="Afegeix un comentari o pregunta"></textarea>
            <button type="submit" style="padding: 0.5rem 0.5rem; width: 50%" class="btn btn-primary">
                Submit
            </button>
        </form>
        <ul id="comments-list" >
            @if(comments.isEmpty())
                <div id="new-comments-container">
                    <p class="w-100 text-center">Vaja... Ningú ha comentat res encara</p>
                </div>
            @endif
            @for(entities.CommentDao comment : comments)
                <li class="comment w-full flex flex-column">
                    <div class="w-100 flex flex-row justify-between" style="margin-bottom: 0.5rem">
                        <p class="comment-user">${comment.getUserEmail()}</p>
                        <p class="comment-user">${comment.getCreatedAt().toString()}</p>
                    </div>
                    <p class="comment-content">${comment.getCommentContent()}</p>
                    @if(comment.getNestedComments() > 0)
                        <button class="text-button" hx-get="/api/v1/feedback?fileId=${fileId}&parentCommentId=${comment.getId()}" hx-trigger="click" hx-target="#comments-list" hx-swap="afterbegin" hx-target-error="#toast">
                            +${comment.getNestedComments()} respostes
                        </button>
                    @endif
                </li>
            @endfor
        </ul>
    </div>
@else
    @for(entities.CommentDao comment : comments)
        <li class="comment w-full flex flex-column">
            <div class="w-100 flex flex-row justify-between" style="margin-bottom: 0.5rem">
                <p class="comment-user">${comment.getUserEmail()}</p>
                <p class="comment-user">${comment.getCreatedAt().toString()}</p>
            </div>
            <p class="comment-content">${comment.getCommentContent()}</p>
            @if(comment.getNestedComments() > 0)
                <button class="text-button" hx-post="/api/v1/feedback?fileId=${fileId}&parentCommentId=${comment.getId()}" hx-trigger="click" hx-target="#comments-list" hx-swap="afterbegin" hx-target-error="#toast">
                    +${comment.getNestedComments()} respostes
                </button>
            @endif
        </li>
    @endfor
@endif
