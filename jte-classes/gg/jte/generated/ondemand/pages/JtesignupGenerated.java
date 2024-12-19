package gg.jte.generated.ondemand.pages;
@SuppressWarnings("unchecked")
public final class JtesignupGenerated {
	public static final String JTE_NAME = "pages/signup.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,3,3,3,3,44,44,44,44,44,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String appTitle, boolean isUserLogged) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"overflow-hidden relative flex flex-column align-center justify-center w-100 h-100\">\n        <div class=\"floating-image\">\n            <img class=\"w-100 h-100\" src=\"https://i.ytimg.com/vi/qfmMgtdz6TA/maxresdefault.jpg\" alt=\"Foto del etseib\" />\n        </div>\n        <div id=\"auth-card\">\n            <ul class=\"tabs\">\n                <li><a href=\"/sign-in\">Inicia sessió</a></li>\n                <li class=\"active\"><a href=\"/sign-up\">Registra't</a></li>\n            </ul>\n            <h1 class=\"title\">Registra't</h1>\n            <h4 class=\"subtitle\">Per accedir a centenars<br/>de recursos acadèmics totalment gratuits</h4>\n            <form hx-post=\"/api/v1/auth/sign-in\" hx-trigger=\"submit\" hx-target=\"#auth-card\" method=\"post\">\n                <div class=\"field\">\n                    <p class=\"control has-icons-left has-icons-right\">\n                        <input class=\"input\" type=\"email\" id=\"email\" name=\"email\"\n                               placeholder=\"nom.cognom1.cognom2@estudiantat.upc.edu\" required>\n                        <span class=\"icon is-small is-left\">\n                            <i class=\"fas fa-envelope\"></i>\n                        </span>\n                    </p>\n                </div>\n                <div class=\"field\">\n                    <p class=\"control has-icons-left\">\n                        <input class=\"input\" type=\"password\" id=\"password\" name=\"password\"\n                               placeholder=\"La teva contrasenya\" required />\n                        <span class=\"icon is-small is-left\">\n                            <i class=\"fas fa-lock\"></i>\n                        </span>\n                    </p>\n                </div>\n                <div class=\"field\">\n                    <p class=\"control\">\n                        <button type=\"submit\" class=\"button is-success\">\n                            Inicia Sessió\n                        </button>\n                    </p>\n                </div>\n            </form>\n        </div>\n    </div>\n");
			}
		}, appTitle, isUserLogged);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String appTitle = (String)params.get("appTitle");
		boolean isUserLogged = (boolean)params.get("isUserLogged");
		render(jteOutput, jteHtmlInterceptor, appTitle, isUserLogged);
	}
}
