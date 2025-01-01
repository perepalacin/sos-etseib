package gg.jte.generated.ondemand.responses.feedback;
@SuppressWarnings("unchecked")
public final class JterootfilecommentsGenerated {
	public static final String JTE_NAME = "responses/feedback/root-file-comments.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,4,4,4,7,7,7,7,14,14,18,18,19,19,22,22,22,23,23,23,25,25,25,26,26,27,27,27,27,27,27,27,27,28,28,28,30,30,32,32,35,35,36,36,39,39,39,40,40,40,42,42,42,43,43,44,44,44,44,44,44,44,44,45,45,45,47,47,49,49,50,50,51,51,51,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, int fileId, java.util.List<entities.CommentDao> comments, int parentCommentId) {
		jteOutput.writeContent("\n");
		if (parentCommentId == -1) {
			jteOutput.writeContent("\n    <div class=\"w-100 flex flex-column gap-0-5\">\n        <h2 style=\"margin-bottom: 0.5rem\">Comentaris i preguntes</h2>\n        <form hx-post=\"/api/v1/feedback?fileId=");
			jteOutput.setContext("form", "hx-post");
			jteOutput.writeUserContent(fileId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-trigger=\"submit\" hx-target=\"#new-comments-container\" hx-swap=\"outerHTML\" hx-target-error=\"#toast\" class=\"flex flex-column gap-0-5 align-end mb-1\" >\n            <textarea type=\"text\" name=\"commentContent\" maxlength=\"250\" class=\"w-100 comment-input\" placeholder=\"Afegeix un comentari o pregunta\"></textarea>\n            <button type=\"submit\" style=\"padding: 0.5rem 0.5rem; width: 50%\" class=\"btn btn-primary\">\n                Submit\n            </button>\n        </form>\n        <ul id=\"comments-list\" >\n            ");
			if (comments.isEmpty()) {
				jteOutput.writeContent("\n                <div id=\"new-comments-container\">\n                    <p class=\"w-100 text-center\">Vaja... Ningú ha comentat res encara</p>\n                </div>\n            ");
			}
			jteOutput.writeContent("\n            ");
			for (entities.CommentDao comment : comments) {
				jteOutput.writeContent("\n                <li class=\"comment w-full flex flex-column\">\n                    <div class=\"w-100 flex flex-row justify-between\" style=\"margin-bottom: 0.5rem\">\n                        <p class=\"comment-user\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getUserEmail());
				jteOutput.writeContent("</p>\n                        <p class=\"comment-user\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getCreatedAt().toString());
				jteOutput.writeContent("</p>\n                    </div>\n                    <p class=\"comment-content\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getCommentContent());
				jteOutput.writeContent("</p>\n                    ");
				if (comment.getNestedComments() > 0) {
					jteOutput.writeContent("\n                        <button class=\"text-button\" hx-get=\"/api/v1/feedback?fileId=");
					jteOutput.setContext("button", "hx-get");
					jteOutput.writeUserContent(fileId);
					jteOutput.setContext("button", null);
					jteOutput.writeContent("&parentCommentId=");
					jteOutput.setContext("button", "hx-get");
					jteOutput.writeUserContent(comment.getId());
					jteOutput.setContext("button", null);
					jteOutput.writeContent("\" hx-trigger=\"click\" hx-target=\"#comments-list\" hx-swap=\"afterbegin\" hx-target-error=\"#toast\">\n                            +");
					jteOutput.setContext("button", null);
					jteOutput.writeUserContent(comment.getNestedComments());
					jteOutput.writeContent(" respostes\n                        </button>\n                    ");
				}
				jteOutput.writeContent("\n                </li>\n            ");
			}
			jteOutput.writeContent("\n        </ul>\n    </div>\n");
		} else {
			jteOutput.writeContent("\n    ");
			for (entities.CommentDao comment : comments) {
				jteOutput.writeContent("\n        <li class=\"comment w-full flex flex-column\">\n            <div class=\"w-100 flex flex-row justify-between\" style=\"margin-bottom: 0.5rem\">\n                <p class=\"comment-user\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getUserEmail());
				jteOutput.writeContent("</p>\n                <p class=\"comment-user\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getCreatedAt().toString());
				jteOutput.writeContent("</p>\n            </div>\n            <p class=\"comment-content\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getCommentContent());
				jteOutput.writeContent("</p>\n            ");
				if (comment.getNestedComments() > 0) {
					jteOutput.writeContent("\n                <button class=\"text-button\" hx-post=\"/api/v1/feedback?fileId=");
					jteOutput.setContext("button", "hx-post");
					jteOutput.writeUserContent(fileId);
					jteOutput.setContext("button", null);
					jteOutput.writeContent("&parentCommentId=");
					jteOutput.setContext("button", "hx-post");
					jteOutput.writeUserContent(comment.getId());
					jteOutput.setContext("button", null);
					jteOutput.writeContent("\" hx-trigger=\"click\" hx-target=\"#comments-list\" hx-swap=\"afterbegin\" hx-target-error=\"#toast\">\n                    +");
					jteOutput.setContext("button", null);
					jteOutput.writeUserContent(comment.getNestedComments());
					jteOutput.writeContent(" respostes\n                </button>\n            ");
				}
				jteOutput.writeContent("\n        </li>\n    ");
			}
			jteOutput.writeContent("\n");
		}
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		int fileId = (int)params.get("fileId");
		java.util.List<entities.CommentDao> comments = (java.util.List<entities.CommentDao>)params.get("comments");
		int parentCommentId = (int)params.get("parentCommentId");
		render(jteOutput, jteHtmlInterceptor, fileId, comments, parentCommentId);
	}
}
