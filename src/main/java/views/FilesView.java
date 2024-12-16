package views;

import java.util.List;

import entities.BreadcrumbsDao;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import entities.FileDao;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

public class FilesView {

    public static String generateDirectoryView(List<String> route, List<FileDao> files) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        templateEngine.addDialect(new LayoutDialect());

        BreadcrumbsDao breadcrumbs = new BreadcrumbsDao(route);

        Context context = new Context();
        context.setVariable("breadcrumbs", breadcrumbs.getItems());
        context.setVariable("files", files);
        context.setVariable("title", "SOS - ETSEIB");
        context.setVariable("appName", "SOS - ETSEIB");

        return templateEngine.process("files", context);
    }
}
