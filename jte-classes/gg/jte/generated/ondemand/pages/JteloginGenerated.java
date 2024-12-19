package gg.jte.generated.ondemand.pages;
@SuppressWarnings("unchecked")
public final class JteloginGenerated {
	public static final String JTE_NAME = "pages/login.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,3,3,3,3,44,44,44,44,44,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String appTitle, boolean isUserLogged) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"overflow-hidden relative flex flex-row w-100 h-100\">\n        <div class=\"floating-image\" style=\"min-width: 40%; background-image: url('https://d129a85b0t6mus.cloudfront.net/public/DigitalAsset/a8465952-7959-436d-a4fa-306f473c2ba4/e20086448dd8152b44cd6048319b0391/full/large/0/default.jpg');\"></div>\n        <div id=\"auth-card\" class=\"auth-card w-100 flex flex-column align-center justify-center gap-2\" style=\"height: 100%\">\n            <ul class=\"tabs\">\n                <li class=\"active\"><a href=\"/sign-in\">Inicia sessió</a></li>\n                <li><a href=\"/sign-up\">Registra't</a></li>\n            </ul>\n            <div class=\"is-full-mobile is-half-tablet\">\n                <h1 class=\"title\">Inicia sessió</h1>\n                <h2 class=\"subtitle\">Accedeix a centenars de recursos acadèmics totalment gratuits</h2>\n                <form hx-post=\"/api/v1/auth/sign-in\" hx-trigger=\"submit\" hx-target=\"#auth-card\" method=\"post\">\n                    <div class=\"field\">\n                        <p class=\"control has-icons-left has-icons-right\">\n                            <input class=\"input\" type=\"email\" id=\"email\" name=\"email\"\n                                   placeholder=\"nom.cognom1.cognom2@estudiantat.upc.edu\" required>\n                            <span class=\"icon is-small is-left\">\n                                <i class=\"fas fa-envelope\"></i>\n                            </span>\n                        </p>\n                    </div>\n                    <div class=\"field\">\n                        <p class=\"control has-icons-left\">\n                            <input class=\"input\" type=\"password\" id=\"password\" name=\"password\"\n                                   placeholder=\"La teva contrasenya\" required>\n                            <span class=\"icon is-small is-left\">\n                                <i class=\"fas fa-lock\"></i>\n                            </span>\n                        </p>\n                    </div>\n                    <div class=\"field\">\n                        <p class=\"control\">\n                            <button type=\"submit\" class=\"button is-success\">\n                                Inicia Sessió\n                            </button>\n                        </p>\n                    </div>\n                </form>\n            </div>\n        </div>\n    </div>\n");
			}
		}, appTitle, isUserLogged);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String appTitle = (String)params.get("appTitle");
		boolean isUserLogged = (boolean)params.get("isUserLogged");
		render(jteOutput, jteHtmlInterceptor, appTitle, isUserLogged);
	}
}
