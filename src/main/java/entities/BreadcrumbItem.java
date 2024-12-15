package entities;

public class BreadcrumbItem {
    private String crumb;
    private String route;

    public BreadcrumbItem(String crumb, String route) {
        this.crumb = crumb;
        this.route = route;
    }

    public String getCrumb() {
        return crumb;
    }

    public String getRoute() {
        return route;
    }
}