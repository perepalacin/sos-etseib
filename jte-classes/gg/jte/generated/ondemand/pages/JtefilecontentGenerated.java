package gg.jte.generated.ondemand.pages;
@SuppressWarnings("unchecked")
public final class JtefilecontentGenerated {
	public static final String JTE_NAME = "pages/file-content.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,8,8,8,8,9,9,10,10,10,12,12,14,14,14,19,19,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,21,36,36,37,37,37,37,39,39,41,41,41,41,41,41,41,42,42,43,43,43,43,43,0,1,2,3,4,5,6,6,6,6};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String fileName, String fileType, int fileId, String content, String base64Content, String mimeType, String programmingLanguage) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n");
				if ("text".equals(fileType)) {
					jteOutput.writeContent("\n    <pre>");
					jteOutput.setContext("pre", null);
					jteOutput.writeUserContent(content);
					jteOutput.writeContent("</pre>\n\n");
				} else if ("code".equals(fileType)) {
					jteOutput.writeContent("\n    <pre class=\"w-100\">\n        <code style=\"background-color: white\">");
					jteOutput.setContext("code", null);
					jteOutput.writeUserContent(content);
					jteOutput.writeContent("</code>\n    </pre>\n    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/default.min.css\">\n    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js\"></script>\n    <script>hljs.highlightAll();</script>\n");
				} else if ("image".equals(fileType)) {
					jteOutput.writeContent("\n    <div>\n        <img class=\"image-zoom\" id=\"image\" src=\"data:");
					jteOutput.setContext("img", "src");
					jteOutput.writeUserContent(mimeType);
					jteOutput.setContext("img", null);
					jteOutput.writeContent(";base64,");
					jteOutput.setContext("img", "src");
					jteOutput.writeUserContent(base64Content);
					jteOutput.setContext("img", null);
					jteOutput.writeContent("\"");
					var __jte_html_attribute_0 = fileName;
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" alt=\"");
						jteOutput.setContext("img", "alt");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("img", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">\n    </div>\n    <script\n        src=\"https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.10.5/viewer.min.js\"\n        integrity=\"sha512-i5q29evO2Z4FHGCO+d5VLrwgre/l+vaud5qsVqQbPXvHmD9obORDrPIGFpP2+ep+HY+z41kAmVFRHqQAjSROmA==\"\n        crossorigin=\"anonymous\"\n        referrerpolicy=\"no-referrer\"\n    ></script>\n    <link\n        rel=\"stylesheet\"\n        href=\"https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.10.5/viewer.css\"\n        integrity=\"sha512-c7kgo7PyRiLnl7mPdTDaH0dUhJMpij4aXRMOHmXaFCu96jInpKc8sZ2U6lby3+mOpLSSlAndRtH6dIonO9qVEQ==\"\n        crossorigin=\"anonymous\"\n        referrerpolicy=\"no-referrer\"\n    />\n");
				} else if ("pdf".equals(fileType)) {
					jteOutput.writeContent("\n    <embed src=\"data:application/pdf;base64,");
					jteOutput.setContext("embed", "src");
					jteOutput.writeUserContent(base64Content);
					jteOutput.setContext("embed", null);
					jteOutput.writeContent("\" type=\"application/pdf\" width=\"100%\" height=\"100%\" />\n\n");
				} else {
					jteOutput.writeContent("\n    <p>The file format is not supported for direct viewing. Please download the file.</p>\n    <a href=\"/files/download/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(fileName);
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\" download>Download ");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(fileName);
					jteOutput.writeContent("</a>\n");
				}
				jteOutput.writeContent("\n");
			}
		}, fileName, true, fileId);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String fileName = (String)params.get("fileName");
		String fileType = (String)params.get("fileType");
		int fileId = (int)params.get("fileId");
		String content = (String)params.get("content");
		String base64Content = (String)params.get("base64Content");
		String mimeType = (String)params.get("mimeType");
		String programmingLanguage = (String)params.get("programmingLanguage");
		render(jteOutput, jteHtmlInterceptor, fileName, fileType, fileId, content, base64Content, mimeType, programmingLanguage);
	}
}
