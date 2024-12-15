package views;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ToastView {

    public static String generateToast(String title, String message, String type) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        templateEngine.addDialect(new LayoutDialect());

        Context context = new Context();
        context.setVariable("toastTitle", title);
        context.setVariable("toastMessage", message);
        context.setVariable("toastType", type);
        return templateEngine.process("toast", context);
    }

}
