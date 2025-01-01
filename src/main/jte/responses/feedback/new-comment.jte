@param entities.CommentDao comment
@param int fileId
@param boolean isNestedComment

@if(!isNestedComment)<div id="new-comments-container"></div>@endif
<li class="comment w-full flex flex-column">
    <div class="w-100 flex flex-row justify-between" style="margin-bottom: 0.5rem">
        <p class="comment-user">${comment.getUserEmail()}</p>
        <p class="comment-user">${new java.util.Date().toString()}</p>
    </div>
    <p class="comment-content">${comment.getCommentContent()}</p>
    @if(!isNestedComment)
        <ul style="padding-left: 1rem" class="mt-1" id="nested-comments-${comment.getId()}"></ul>
        <div class="w-100 flex flex-row justify-end">
            <form id="response-form-${comment.getId()}" hx-post="/api/v1/feedback?fileId=${fileId}&parentCommentId=${comment.getId()}" hx-trigger="submit" hx-target="#nested-comments-${comment.getId()}" hx-swap="beforeend" hx-target-error="#toast" class="w-100 display-none gap-0-5 align-end mt-1" hx-on:htmx:before-swap="if (event.detail.shouldSwap) this.reset();">
                <textarea type="text" name="commentContent" maxlength="250" class="w-100 comment-input" placeholder="Escriu la teva resposta"></textarea>
                <button type="submit" style="padding: 0.5rem 0.5rem; width: 50%" class="btn btn-primary">
                    Respondre
                </button>
            </form>
            <button id="response-trigger-${comment.getId()}" onclick="handleOpenResponseForm('response-form-${comment.getId()}', 'response-trigger-${comment.getId()}')" type="button" style="padding: 0" class="text-button">
                Respondre
            </button>
        </div>
    @endif
</li>


