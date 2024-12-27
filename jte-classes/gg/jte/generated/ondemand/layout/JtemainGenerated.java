package gg.jte.generated.ondemand.layout;
@SuppressWarnings("unchecked")
public final class JtemainGenerated {
	public static final String JTE_NAME = "layout/main.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,28,28,28,42,42,51,51,57,57,57,73,73,73,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, gg.jte.Content content, String appName, boolean isUserLogged) {
		jteOutput.writeContent("<!DOCTYPE html>\n<html lang=\"cat\">\n<head>\n    <meta charset=\"UTF-8\" name=\"htmx-config\" content='{\"responseHandling\": [{\"code\":\".*\", \"swap\": true}]}'>\n    <link rel=\"icon\" href=\"data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>🎓</text></svg>\">\n    <title>SOS - ETSEIB</title>\n    <script src=\"https://unpkg.com/htmx.org@2.0.3\"\n            integrity=\"sha384-0895/pl2MU10Hqc6jd4RvrthNlDiE9U1tWmX7WRESftEDRosgxNsQG/Ze9YMRzHq\"\n            crossorigin=\"anonymous\"></script>\n    <script src=\"https://unpkg.com/htmx-ext-response-targets@2.0.0/response-targets.js\"></script>\n    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n    <link href=\"https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap\" rel=\"stylesheet\">\n    <link href=\"/static/styles.css\" rel=\"stylesheet\">\n    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/default.min.css\">\n    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js\"></script>\n\n</head>\n<body>\n<header>\n    <nav class=\"w-100\">\n        <div class=\"w-100 flex flex-row align-center justify-between\">\n            <a href=\"/\" class=\"logo\">\n                SOS - ETSEIB\n            </a>\n            ");
		if (isUserLogged) {
			jteOutput.writeContent("\n                <form class=\"search-div w-40\" >\n                    <i class=\"absolute fa-solid fa-magnifying-glass\" style=\"color: #454746;\" ></i>\n                    <input \n                        class=\"search-input\"\n                        name=\"search\"\n                        id=\"search-input\"\n                        placeholder=\"Cerca un arxiu o carpeta\"\n                    />\n                </form>\n                <button hx-post=\"/api/v1/auth/log-out\" hx-target=\"body\" hx-push-url=\"true\" class=\"btn btn-primary\">\n                    <i class=\"fa-solid fa-arrow-right-from-bracket\"></i>\n                    <p>Tanca Sessió</p>\n                </button>\n            ");
		} else {
			jteOutput.writeContent("\n                <div class=\"flex flex-row gap-1\">\n                    <button class=\"btn btn-secondary\">\n                        Registra't\n                    </button>\n                    <button class=\"btn btn-primary\">\n                        Inicia Sessió\n                    </button>\n                </div>\n            ");
		}
		jteOutput.writeContent("\n\n        </div>\n    </nav>\n</header>\n<div class=\"content\">\n    ");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n</div>\n<footer>\n    <p>\n        <strong>SOS - ETSEIB</strong> cap dret restringit per a qualsevol dubte o consulta, envieu un correu a\n        <a href=\"https://jgthms.com\">info@sos-etseib.com</a>.\n        El contingut de la web esta llicenciat\n        <a href=\"https://creativecommons.org/licenses/by-nc-sa/4.0//\">CC BY NC SA 4.0</a>.\n    </p>\n</footer>\n<script src=\"https://kit.fontawesome.com/07d92569e3.js\" crossorigin=\"anonymous\"></script>\n<script src=\"/static/scripts.js\"></script>\n\n</body>\n\n</html>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		gg.jte.Content content = (gg.jte.Content)params.get("content");
		String appName = (String)params.get("appName");
		boolean isUserLogged = (boolean)params.get("isUserLogged");
		render(jteOutput, jteHtmlInterceptor, content, appName, isUserLogged);
	}
}
