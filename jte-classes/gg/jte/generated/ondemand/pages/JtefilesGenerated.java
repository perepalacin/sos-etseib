package gg.jte.generated.ondemand.pages;
@SuppressWarnings("unchecked")
public final class JtefilesGenerated {
	public static final String JTE_NAME = "pages/files.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,5,5,6,6,7,7,10,10,10,14,14,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,18,18,20,20,21,21,25,25,29,29,30,30,32,32,32,32,32,32,32,32,32,34,34,34,37,37,38,38,41,41,42,42,55,55,56,56,59,59,59,59,59,59,59,59,59,60,60,62,62,64,64,66,66,68,68,70,70,72,72,76,76,76,76,76,76,76,76,76,77,77,77,81,81,81,81,81,81,81,81,81,82,82,82,86,86,86,86,86,86,86,86,86,87,87,87,91,91,91,91,91,91,91,91,91,96,96,97,97,100,100,104,104,104,104,104,0,1,2,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String appTitle, java.util.List<entities.BreadcrumbItem> breadcrumbs, String tabName, boolean isUserLogged, java.util.List<entities.FileDao> files) {
		String lastCrumb = breadcrumbs.getLast().getRoute();
		jteOutput.writeContent("\n");
		boolean hasFolders = files.stream().anyMatch(fileDao -> "folder".equals(fileDao.getType()));
		jteOutput.writeContent("\n");
		boolean hasFiles = files.stream().anyMatch(fileDao -> "file".equals(fileDao.getType()));
		jteOutput.writeContent("\n\n\n");
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"w-100 flex flex-column gap-1 align-start\">\n        <nav aria-label=\"breadcrumb\" class=\"w-100\">\n            <ol class=\"breadcrumb w-100\">\n                ");
				for (entities.BreadcrumbItem breadcrumb : breadcrumbs) {
					jteOutput.writeContent("\n                    <li>\n                        <a");
					var __jte_html_attribute_0 = breadcrumb.getRoute().equals(breadcrumbs.getLast().getRoute());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" aria-current=\"");
						jteOutput.setContext("a", "aria-current");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					var __jte_html_attribute_1 = breadcrumb.getRoute();
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_1);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(breadcrumb.getCrumb());
					jteOutput.writeContent("</a>\n                    </li>\n                    ");
					if (!breadcrumb.getRoute().equals(breadcrumbs.getLast().getRoute())) {
						jteOutput.writeContent("\n                        <p style=\"margin: 0rem;\">></p>\n                    ");
					}
					jteOutput.writeContent("\n                ");
				}
				jteOutput.writeContent("\n            </ol>\n        </nav>\n\n        ");
				if (hasFolders) {
					jteOutput.writeContent("\n            <h4 class=\"semi-bold\">Folders</h4>\n            <nav class=\"w-100\">\n                <ul class=\"folders-list\">\n                    ");
					for (entities.FileDao file : files) {
						jteOutput.writeContent("\n                        ");
						if ("folder".equals(file.getType())) {
							jteOutput.writeContent("\n                            <li>\n                                <a");
							var __jte_html_attribute_2 = lastCrumb + "/" + file.getName();
							if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_2)) {
								jteOutput.writeContent(" href=\"");
								jteOutput.setContext("a", "href");
								jteOutput.writeUserContent(__jte_html_attribute_2);
								jteOutput.setContext("a", null);
								jteOutput.writeContent("\"");
							}
							jteOutput.writeContent(" class=\"w-100 flex flex-row align-center justify-start gap-1\">\n                                    <i class=\"fa-solid fa-folder\" style=\"color: #55585d\"></i>\n                                    <p>");
							jteOutput.setContext("p", null);
							jteOutput.writeUserContent(file.getName());
							jteOutput.writeContent("</p>\n                                </a>\n                            </li>\n                        ");
						}
						jteOutput.writeContent("\n                    ");
					}
					jteOutput.writeContent("\n                </ul>\n            </nav>\n        ");
				}
				jteOutput.writeContent("\n        ");
				if (hasFiles) {
					jteOutput.writeContent("\n            <h4 class=\"semi-bold\">Files</h4>\n            <nav class=\"w-100\">\n                <table class=\"w-100\">\n                    <tr class=\"w-100\">\n                        <th style=\"width: auto; text-align: center; padding: 0rem 0.5rem;\">\n                            <i class=\"fa-solid fa-bars\"></i>\n                        </th>\n                        <th class=\"text-start\" style=\"width: 50%;\">Name</th>\n                        <th class=\"text-start\"  style=\"width: auto;\">Compartit per</th>\n                        <th class=\"text-start\"  style=\"width: auto;\">Compartit el</th>\n                        <th class=\"text-start\"  style=\"width: auto;\">Puntuació</th>\n                    </tr>\n                    ");
					for (entities.FileDao file : files) {
						jteOutput.writeContent("\n                        ");
						if ("file".equals(file.getType())) {
							jteOutput.writeContent("\n                            <tr class=\"w-100\">\n                                <td>\n                                    <a class=\"flex w-100 justify-center\"");
							var __jte_html_attribute_3 = lastCrumb + '/' + file.getName();
							if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_3)) {
								jteOutput.writeContent(" href=\"");
								jteOutput.setContext("a", "href");
								jteOutput.writeUserContent(__jte_html_attribute_3);
								jteOutput.setContext("a", null);
								jteOutput.writeContent("\"");
							}
							jteOutput.writeContent(">\n                                    ");
							if (file.getName().endsWith(".doc") || file.getName().endsWith(".docx")) {
								jteOutput.writeContent("\n                                        <i class=\"fa-regular fa-file-word\" style=\"color: #4472ee;\"></i>\n                                    ");
							} else if (file.getName().endsWith(".pdf")) {
								jteOutput.writeContent("\n                                        <i class=\"fa-regular fa-file-pdf\" style=\"color: #d94932;\"></i>\n                                    ");
							} else if (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls") ||file.getName().endsWith(".xlsm") || file.getName().endsWith(".csv")) {
								jteOutput.writeContent("\n                                        <i class=\"fa-regular fa-file-table\" style=\"color: #469d4e;\"></i>\n                                    ");
							} else if (file.getName().endsWith(".ppt") || file.getName().endsWith(".pptx")) {
								jteOutput.writeContent("\n                                        <i class=\"fa-regular fa-file-powerpoint\" style=\"color: #f3ba28;\"></i>\n                                    ");
							} else if (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".png") || file.getName().endsWith(".webp") || file.getName().endsWith(".gif")) {
								jteOutput.writeContent("\n                                        <i class=\"fa-regular fa-image\" style=\"color: #55585d;\"></i>\n                                    ");
							} else {
								jteOutput.writeContent("\n                                        <i class=\"fa-regular fa-file-lines\" style=\"color: #4472ee;\"></i>\n                                    ");
							}
							jteOutput.writeContent("\n                                    </a>\n                                </td>\n                                <td>\n                                    <a class=\"py-0-5 flex w-100\"");
							var __jte_html_attribute_4 = lastCrumb + "/" + file.getName();
							if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_4)) {
								jteOutput.writeContent(" href=\"");
								jteOutput.setContext("a", "href");
								jteOutput.writeUserContent(__jte_html_attribute_4);
								jteOutput.setContext("a", null);
								jteOutput.writeContent("\"");
							}
							jteOutput.writeContent(">\n                                        ");
							jteOutput.setContext("a", null);
							jteOutput.writeUserContent(file.getName());
							jteOutput.writeContent("\n                                    </a>\n                                </td>\n                                <td class=\"font-light\">\n                                    <a class=\"flex w-100\"");
							var __jte_html_attribute_5 = lastCrumb + "/" + file.getName();
							if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_5)) {
								jteOutput.writeContent(" href=\"");
								jteOutput.setContext("a", "href");
								jteOutput.writeUserContent(__jte_html_attribute_5);
								jteOutput.setContext("a", null);
								jteOutput.writeContent("\"");
							}
							jteOutput.writeContent(">\n                                        ");
							jteOutput.setContext("a", null);
							jteOutput.writeUserContent(file.getSharedBy());
							jteOutput.writeContent("\n                                    </a>\n                                </td>\n                                <td class=\"font-light\">\n                                    <a class=\"flex w-100\"");
							var __jte_html_attribute_6 = lastCrumb + "/" + file.getName();
							if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_6)) {
								jteOutput.writeContent(" href=\"");
								jteOutput.setContext("a", "href");
								jteOutput.writeUserContent(__jte_html_attribute_6);
								jteOutput.setContext("a", null);
								jteOutput.writeContent("\"");
							}
							jteOutput.writeContent(">\n                                        ");
							jteOutput.setContext("a", null);
							jteOutput.writeUserContent(file.getCreatedAt().toString());
							jteOutput.writeContent("\n                                    </a>\n                                </td>\n                                <td>\n                                    <a class=\"flex w-100\"");
							var __jte_html_attribute_7 = lastCrumb + "/" + file.getName();
							if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_7)) {
								jteOutput.writeContent(" href=\"");
								jteOutput.setContext("a", "href");
								jteOutput.writeUserContent(__jte_html_attribute_7);
								jteOutput.setContext("a", null);
								jteOutput.writeContent("\"");
							}
							jteOutput.writeContent(">\n                                        4.75/5\n                                    </a>\n                                </td>\n                            </tr>\n                        ");
						}
						jteOutput.writeContent("\n                    ");
					}
					jteOutput.writeContent("\n                </table>\n            </nav>\n        ");
				}
				jteOutput.writeContent("\n    </div>\n\n    </html>\n");
			}
		}, tabName, true);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String appTitle = (String)params.get("appTitle");
		java.util.List<entities.BreadcrumbItem> breadcrumbs = (java.util.List<entities.BreadcrumbItem>)params.get("breadcrumbs");
		String tabName = (String)params.get("tabName");
		boolean isUserLogged = (boolean)params.get("isUserLogged");
		java.util.List<entities.FileDao> files = (java.util.List<entities.FileDao>)params.get("files");
		render(jteOutput, jteHtmlInterceptor, appTitle, breadcrumbs, tabName, isUserLogged, files);
	}
}
