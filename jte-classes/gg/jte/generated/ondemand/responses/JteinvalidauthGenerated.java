package gg.jte.generated.ondemand.responses;
@SuppressWarnings("unchecked")
public final class JteinvalidauthGenerated {
	public static final String JTE_NAME = "responses/invalid-auth.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,2,2,2,2,2,2,2,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String errorMessage) {
		jteOutput.writeContent("\n<h4 class='error-message'>");
		jteOutput.setContext("h4", null);
		jteOutput.writeUserContent(errorMessage);
		jteOutput.writeContent("</h4>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String errorMessage = (String)params.get("errorMessage");
		render(jteOutput, jteHtmlInterceptor, errorMessage);
	}
}
