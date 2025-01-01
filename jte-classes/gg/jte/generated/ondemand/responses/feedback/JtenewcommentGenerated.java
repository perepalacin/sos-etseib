package gg.jte.generated.ondemand.responses.feedback;
@SuppressWarnings("unchecked")
public final class JtenewcommentGenerated {
	public static final String JTE_NAME = "responses/feedback/new-comment.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,4,4,4,4,4,7,7,7,8,8,8,10,10,10,11,11,12,12,12,12,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,20,20,20,20,20,20,20,20,20,20,20,20,24,24,28,28,28,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, entities.CommentDao comment, int fileId, boolean isNestedComment) {
		jteOutput.writeContent("\n");
		if (!isNestedComment) {
			jteOutput.writeContent("<div id=\"new-comments-container\"></div>");
		}
		jteOutput.writeContent("\n<li class=\"comment w-full flex flex-column\">\n    <div class=\"w-100 flex flex-row justify-between\" style=\"margin-bottom: 0.5rem\">\n        <p class=\"comment-user\">");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(comment.getUserEmail());
		jteOutput.writeContent("</p>\n        <p class=\"comment-user\">");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(new java.util.Date().toString());
		jteOutput.writeContent("</p>\n    </div>\n    <p class=\"comment-content\">");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(comment.getCommentContent());
		jteOutput.writeContent("</p>\n    ");
		if (!isNestedComment) {
			jteOutput.writeContent("\n        <ul style=\"padding-left: 1rem\" class=\"mt-1\" id=\"nested-comments-");
			jteOutput.setContext("ul", "id");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("ul", null);
			jteOutput.writeContent("\"></ul>\n        <div class=\"w-100 flex flex-row justify-end\">\n            <form id=\"response-form-");
			jteOutput.setContext("form", "id");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-post=\"/api/v1/feedback?fileId=");
			jteOutput.setContext("form", "hx-post");
			jteOutput.writeUserContent(fileId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("&parentCommentId=");
			jteOutput.setContext("form", "hx-post");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-trigger=\"submit\" hx-target=\"#nested-comments-");
			jteOutput.setContext("form", "hx-target");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-swap=\"beforeend\" hx-target-error=\"#toast\" class=\"w-100 display-none gap-0-5 align-end mt-1\" hx-on:htmx:before-swap=\"if (event.detail.shouldSwap) this.reset();\">\n                <textarea type=\"text\" name=\"commentContent\" maxlength=\"250\" class=\"w-100 comment-input\" placeholder=\"Escriu la teva resposta\"></textarea>\n                <button type=\"submit\" style=\"padding: 0.5rem 0.5rem; width: 50%\" class=\"btn btn-primary\">\n                    Respondre\n                </button>\n            </form>\n            <button id=\"response-trigger-");
			jteOutput.setContext("button", "id");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("button", null);
			jteOutput.writeContent("\" onclick=\"handleOpenResponseForm('response-form-");
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("button", null);
			jteOutput.writeContent("', 'response-trigger-");
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(comment.getId());
			jteOutput.setContext("button", null);
			jteOutput.writeContent("')\" type=\"button\" style=\"padding: 0\" class=\"text-button\">\n                Respondre\n            </button>\n        </div>\n    ");
		}
		jteOutput.writeContent("\n</li>\n\n\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		entities.CommentDao comment = (entities.CommentDao)params.get("comment");
		int fileId = (int)params.get("fileId");
		boolean isNestedComment = (boolean)params.get("isNestedComment");
		render(jteOutput, jteHtmlInterceptor, comment, fileId, isNestedComment);
	}
}
