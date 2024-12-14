package views;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
public class AuthView {


    public static String generateLoginView() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        templateEngine.addDialect(new LayoutDialect());

        Context context = new Context();
        context.setVariable("title", "SOS - ETSEIB: Inicia Sessió");
        context.setVariable("appName", "SOS - ETSEIB");
        context.setVariable("isUserLogged", false);

        return templateEngine.process("login", context);
    }

    public static String generateSignUpView() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        templateEngine.addDialect(new LayoutDialect());

        Context context = new Context();
        context.setVariable("title", "SOS - ETSEIB: Registra't");
        context.setVariable("appName", "SOS - ETSEIB");
        context.setVariable("isUserLogged", false);

        return templateEngine.process("signup", context);
    }
}
