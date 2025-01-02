@param int fileId
@param java.util.List<entities.CommentDao> comments
@param int parentCommentId

@if(parentCommentId == -1)
    <div class="w-100 flex flex-column gap-0-5 max-height-85" >
        <h2 style="margin-bottom: 0.5rem">Comentaris i preguntes</h2>
        <form hx-post="/api/v1/feedback?fileId=${fileId}" hx-trigger="submit" hx-target="#new-comments-container" hx-swap="outerHTML" hx-target-error="#toast" class="flex flex-column gap-0-5 align-end mb-1" >
            <textarea type="text" name="commentContent" maxlength="250" class="w-100 comment-input" placeholder="Fes un comentari/pregunta"></textarea>
            <button type="submit" style="padding: 0.5rem 0.5rem; width: 50%" class="btn btn-primary">
                Comentar
            </button>
        </form>
        <ul id="comments-list" class="overflow-y-auto">
            @if(comments.isEmpty())
                <div id="new-comments-container">
                    <p class="w-100 text-center">Vaja... Ningú ha comentat res encara</p>
                </div>
            @else
                <div id="new-comments-container"></div>
            @endif
            @for(entities.CommentDao comment : comments)
                <li class="comment w-full flex flex-column">
                    <div class="w-100 flex flex-row justify-between" style="margin-bottom: 0.5rem">
                        <p class="comment-user">${comment.getUserEmail()}</p>
                        <p class="comment-user">${comment.getCreatedAt().toString()}</p>
                    </div>
                    <p class="comment-content">${comment.getCommentContent()}</p>
                    @if(comment.getNestedComments() > 0)
                        <button class="text-button" hx-get="/api/v1/feedback?fileId=${fileId}&parentCommentId=${comment.getId()}" hx-trigger="click" hx-target="this" hx-swap="outerHTML" hx-target-error="#toast">
                            +${comment.getNestedComments()}  ${comment.getNestedComments() > 1 ? "respostes" : "resposta"}
                        </button>
                    @else
                        <ul style="padding-left: 1rem" class="mt-1" id="nested-comments-${comment.getId()}"></ul>
                        <div class="w-100 flex flex-row justify-end">
                            <form id="response-form-${comment.getId()}" hx-post="/api/v1/feedback?fileId=${fileId}&parentCommentId=${comment.getId()}" hx-trigger="submit" hx-target="#nested-comments-${comment.getId()}" hx-swap="afterbegin" hx-target-error="#toast" class="w-100 display-none gap-0-5 align-end mt-1" hx-on:htmx:before-swap="if (event.detail.shouldSwap) this.reset();">
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
            @endfor
        </ul>
    </div>
@else
    <ul style="padding-left: 1rem" class="mt-1" id="nested-comments-${parentCommentId}">
        @for(entities.CommentDao comment : comments)
            <li class="comment w-full flex flex-column">
                <div class="w-100 flex flex-row justify-between" style="margin-bottom: 0.5rem">
                    <p class="comment-user">${comment.getUserEmail()}</p>
                    <p class="comment-user">${comment.getCreatedAt().toString()}</p>
                </div>
                <p class="comment-content">${comment.getCommentContent()}</p>
            </li>
        @endfor
    </ul>
    <div class="w-100 flex flex-row justify-end" style="margin-top: 0.5rem">
        <form id="response-form-${parentCommentId}" hx-post="/api/v1/feedback?fileId=${fileId}&parentCommentId=${parentCommentId}" hx-trigger="submit" hx-target="#nested-comments-${parentCommentId}" hx-swap="beforeend" hx-target-error="#toast" class="w-100 display-none gap-0-5 align-end" hx-on:htmx:before-swap="if (event.detail.shouldSwap) this.reset();"x>
            <textarea type="text" name="commentContent" maxlength="250" class="w-100 comment-input" placeholder="Escriu la teva resposta"></textarea>
            <button type="submit" style="padding: 0.5rem 0.5rem; width: 50%" class="btn btn-primary">
                Respondre
            </button>
        </form>
        <button id="response-trigger-${parentCommentId}" onclick="handleOpenResponseForm('response-form-${parentCommentId}', 'response-trigger-${parentCommentId}')" type="button" style="padding: 0" class="text-button">
            Respondre
        </button>
    </div>
@endif
