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

            templateEngine.render("pages/signup.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            //TODO: return page under maintenance
            e.printStackTrace();
        }
        return "";
    }

    public static String generateCorrectSignUpResponse (TemplateEngine templateEngine) {
        try  {
            TemplateOutput output = new StringOutput();

            templateEngine.render("responses/valid-sign-up.jte", null, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateIncorrectAuthUpView (TemplateEngine templateEngine, String errorMessage) {
        try  {
            TemplateOutput output = new StringOutput();

            Map<String, Object> params = new HashMap<>();
            params.put("errorMessage", errorMessage);

            templateEngine.render("responses/invalid-auth.jte", params, output);
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
