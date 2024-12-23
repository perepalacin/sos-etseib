package gg.jte.generated.ondemand.pages;
@SuppressWarnings("unchecked")
public final class JtesignupGenerated {
	public static final String JTE_NAME = "pages/signup.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,2,2,2,2,28,28,28,28,28,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String appTitle) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"overflow-hidden relative flex flex-column align-center justify-center w-100 h-100\" hx-ext=\"response-targets\">\n        <div id=\"auth-card\">\n            <ul class=\"tabs\">\n                <li><a href=\"/sign-in\" class=\"btn\">Inicia sessió</a></li>\n                <li class=\"active\"><a class=\"btn btn-primary\" href=\"/sign-up\">Registra't</a></li>\n            </ul>\n            <h1>Benvingut a SOS - ETSEIB</h1>\n            <h3 id=\"sub-title\" class=\"text-center font-normal\">Registra't i accedeix a centernars de recursos acadèmics totalment gratuits</h3>\n            <form class=\"flex flex-column gap-2 w-100 items-center\" hx-post=\"/api/v1/auth/sign-up\" hx-trigger=\"submit\" hx-target=\"#auth-card\" hx-swap=\"outerHTML\" hx-target-error=\"#sub-title\" method=\"post\">\n                <div class=\"search-div\">\n                    <i class=\"absolute fa-solid fa-envelope\" style=\"color: #454746;\" ></i>\n                    <input class=\"search-input\" type=\"email\" id=\"email\" name=\"email\"\n                           placeholder=\"nom.cognom1.cognom2@estudiantat.upc.edu\" required autocomplete=\"username\" />\n                </div>\n                <div class=\"search-div\">\n                    <i class=\"absolute fa-solid fa-lock\" style=\"color: #454746;\" ></i>\n                    <input class=\"search-input\" type=\"password\" id=\"password\" name=\"password\"\n                           placeholder=\"La teva contrassenya\" required autocomplete=\"new-password\" />\n                </div>\n                <button type=\"submit\" class=\"btn btn-primary\" >\n                    Registra't\n                </button>\n            </form>\n        </div>\n    </div>\n");
			}
		}, appTitle, false);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String appTitle = (String)params.get("appTitle");
		render(jteOutput, jteHtmlInterceptor, appTitle);
	}
}
