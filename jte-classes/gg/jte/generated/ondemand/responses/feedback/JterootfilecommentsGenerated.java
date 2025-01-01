package gg.jte.generated.ondemand.responses.feedback;
@SuppressWarnings("unchecked")
public final class JterootfilecommentsGenerated {
	public static final String JTE_NAME = "responses/feedback/root-file-comments.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,4,4,4,7,7,7,7,14,14,18,18,20,20,21,21,24,24,24,25,25,25,27,27,27,28,28,29,29,29,29,29,29,29,29,30,30,30,30,30,30,32,32,33,33,33,33,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,41,41,41,41,41,41,41,41,41,41,41,41,45,45,47,47,50,50,51,51,51,51,52,52,55,55,55,56,56,56,58,58,58,60,60,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,69,69,69,69,69,69,69,69,69,69,69,69,73,73,74,74,74,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, int fileId, java.util.List<entities.CommentDao> comments, int parentCommentId) {
		jteOutput.writeContent("\n");
		if (parentCommentId == -1) {
			jteOutput.writeContent("\n    <div class=\"w-100 flex flex-column gap-0-5 max-height-85\" >\n        <h2 style=\"margin-bottom: 0.5rem\">Comentaris i preguntes</h2>\n        <form hx-post=\"/api/v1/feedback?fileId=");
			jteOutput.setContext("form", "hx-post");
			jteOutput.writeUserContent(fileId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-trigger=\"submit\" hx-target=\"#new-comments-container\" hx-swap=\"outerHTML\" hx-target-error=\"#toast\" class=\"flex flex-column gap-0-5 align-end mb-1\" >\n            <textarea type=\"text\" name=\"commentContent\" maxlength=\"250\" class=\"w-100 comment-input\" placeholder=\"Fes un comentari/pregunta\"></textarea>\n            <button type=\"submit\" style=\"padding: 0.5rem 0.5rem; width: 50%\" class=\"btn btn-primary\">\n                Comentar\n            </button>\n        </form>\n        <ul id=\"comments-list\" class=\"overflow-y-auto\">\n            ");
			if (comments.isEmpty()) {
				jteOutput.writeContent("\n                <div id=\"new-comments-container\">\n                    <p class=\"w-100 text-center\">Vaja... Ningú ha comentat res encara</p>\n                </div>\n            ");
			} else {
				jteOutput.writeContent("\n                <div id=\"new-comments-container\"></div>\n            ");
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
					jteOutput.writeContent("\" hx-trigger=\"click\" hx-target=\"this\" hx-swap=\"outerHTML\" hx-target-error=\"#toast\">\n                            +");
					jteOutput.setContext("button", null);
					jteOutput.writeUserContent(comment.getNestedComments());
					jteOutput.writeContent("  ");
					jteOutput.setContext("button", null);
					jteOutput.writeUserContent(comment.getNestedComments() > 1 ? "respostes" : "resposta");
					jteOutput.writeContent("\n                        </button>\n                    ");
				} else {
					jteOutput.writeContent("\n                        <ul style=\"padding-left: 1rem\" class=\"mt-1\" id=\"nested-comments-");
					jteOutput.setContext("ul", "id");
					jteOutput.writeUserContent(comment.getId());
					jteOutput.setContext("ul", null);
					jteOutput.writeContent("\"></ul>\n                        <div class=\"w-100 flex flex-row justify-end\">\n                            <form id=\"response-form-");
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
					jteOutput.writeContent("\" hx-swap=\"afterbegin\" hx-target-error=\"#toast\" class=\"w-100 display-none gap-0-5 align-end mt-1\" hx-on:htmx:before-swap=\"if (event.detail.shouldSwap) this.reset();\">\n                                <textarea type=\"text\" name=\"commentContent\" maxlength=\"250\" class=\"w-100 comment-input\" placeholder=\"Escriu la teva resposta\"></textarea>\n                                <button type=\"submit\" style=\"padding: 0.5rem 0.5rem; width: 50%\" class=\"btn btn-primary\">\n                                    Respondre\n                                </button>\n                            </form>\n                            <button id=\"response-trigger-");
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
					jteOutput.writeContent("')\" type=\"button\" style=\"padding: 0\" class=\"text-button\">\n                                Respondre\n                            </button>\n                        </div>\n                    ");
				}
				jteOutput.writeContent("\n                </li>\n            ");
			}
			jteOutput.writeContent("\n        </ul>\n    </div>\n");
		} else {
			jteOutput.writeContent("\n    <ul style=\"padding-left: 1rem\" class=\"mt-1\" id=\"nested-comments-");
			jteOutput.setContext("ul", "id");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("ul", null);
			jteOutput.writeContent("\">\n        ");
			for (entities.CommentDao comment : comments) {
				jteOutput.writeContent("\n            <li class=\"comment w-full flex flex-column\">\n                <div class=\"w-100 flex flex-row justify-between\" style=\"margin-bottom: 0.5rem\">\n                    <p class=\"comment-user\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getUserEmail());
				jteOutput.writeContent("</p>\n                    <p class=\"comment-user\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getCreatedAt().toString());
				jteOutput.writeContent("</p>\n                </div>\n                <p class=\"comment-content\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(comment.getCommentContent());
				jteOutput.writeContent("</p>\n            </li>\n        ");
			}
			jteOutput.writeContent("\n    </ul>\n    <div class=\"w-100 flex flex-row justify-end\" style=\"margin-top: 0.5rem\">\n        <form id=\"response-form-");
			jteOutput.setContext("form", "id");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-post=\"/api/v1/feedback?fileId=");
			jteOutput.setContext("form", "hx-post");
			jteOutput.writeUserContent(fileId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("&parentCommentId=");
			jteOutput.setContext("form", "hx-post");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-trigger=\"submit\" hx-target=\"#nested-comments-");
			jteOutput.setContext("form", "hx-target");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" hx-swap=\"beforeend\" hx-target-error=\"#toast\" class=\"w-100 display-none gap-0-5 align-end\" hx-on:htmx:before-swap=\"if (event.detail.shouldSwap) this.reset();\">\n            <textarea type=\"text\" name=\"commentContent\" maxlength=\"250\" class=\"w-100 comment-input\" placeholder=\"Escriu la teva resposta\"></textarea>\n            <button type=\"submit\" style=\"padding: 0.5rem 0.5rem; width: 50%\" class=\"btn btn-primary\">\n                Respondre\n            </button>\n        </form>\n        <button id=\"response-trigger-");
			jteOutput.setContext("button", "id");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("button", null);
			jteOutput.writeContent("\" onclick=\"handleOpenResponseForm('response-form-");
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("button", null);
			jteOutput.writeContent("', 'response-trigger-");
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(parentCommentId);
			jteOutput.setContext("button", null);
			jteOutput.writeContent("')\" type=\"button\" style=\"padding: 0\" class=\"text-button\">\n            Respondre\n        </button>\n    </div>\n");
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
