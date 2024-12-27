package gg.jte.generated.ondemand.pages;
@SuppressWarnings("unchecked")
public final class JtefilecontentGenerated {
	public static final String JTE_NAME = "pages/file-content.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,7,7,7,7,8,8,9,9,9,11,11,13,13,13,18,18,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,22,24,24,25,25,25,25,27,27,29,29,29,29,29,29,29,30,30,31,31,31,31,31,0,1,2,3,4,5,5,5,5};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String fileName, String fileType, String content, String base64Content, String mimeType, String programmingLanguage) {
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
					jteOutput.writeContent("\n    <div>\n        <img id=\"image\" src=\"data:");
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
					jteOutput.writeContent(">\n    </div>\n");
					jteOutput.writeContent("\n    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.11.7/viewer.min.js\" integrity=\"sha512-lZD0JiwhtP4UkFD1mc96NiTZ14L7MjyX5Khk8PMxJszXMLvu7kjq1sp4bb0tcL6MY+/4sIuiUxubOqoueHrW4w==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\"></script>\n");
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
		}, fileName, true);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String fileName = (String)params.get("fileName");
		String fileType = (String)params.get("fileType");
		String content = (String)params.get("content");
		String base64Content = (String)params.get("base64Content");
		String mimeType = (String)params.get("mimeType");
		String programmingLanguage = (String)params.get("programmingLanguage");
		render(jteOutput, jteHtmlInterceptor, fileName, fileType, content, base64Content, mimeType, programmingLanguage);
	}
}
