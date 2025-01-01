@param entities.CommentDao comment
@param int fileId
@param boolean isNestedComment

<div id="new-comments-container">
</div>
<li class="comment w-full flex flex-column">
    <div class="w-100 flex flex-row justify-between" style="margin-bottom: 0.5rem">
        <p class="comment-user">${comment.getUserEmail()}</p>
        <p class="comment-user">${new java.util.Date().toString()}</p>
    </div>
    <p class="comment-content">${comment.getCommentContent()}</p>
</li>
