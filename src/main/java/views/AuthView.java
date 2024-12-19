package views;

import entities.BreadcrumbsDao;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;

import java.util.HashMap;
import java.util.Map;

public class AuthView {


    public static String generateLoginView(TemplateEngine templateEngine) {
        try {
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("appTitle", "SOS - ETSEIB: Inicia Sessió");
            params.put("isUserLogged", false);

            templateEngine.render("pages/login.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateSignUpView(TemplateEngine templateEngine) {
        try  {

        TemplateOutput output = new StringOutput();

        Map<String, Object> params = new HashMap<>();
        params.put("appTitle", "SOS - ETSEIB: Registra't");
        params.put("isUserLogged", true);

        templateEngine.render("pages/signup.jte", params, output);
        return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
