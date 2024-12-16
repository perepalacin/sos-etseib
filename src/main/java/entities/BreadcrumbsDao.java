package entities;

import java.util.ArrayList;
import java.util.List;

public class BreadcrumbsDao {
    private List<BreadcrumbItem> items;

    public BreadcrumbsDao(List<String> routes) {
        this.items = new ArrayList<>();
        this.generateCrumbs(routes);
    }

    private void generateCrumbs(List<String> routes) {
        StringBuilder path = new StringBuilder("/files");
        for (String route : routes) {
            path.append("/").append(route);
            String formattedRoute = capitalizeWords(route);
            this.items.add(new BreadcrumbItem(formattedRoute, path.toString()));
        }
    }

    private String capitalizeWords(String input) {
        String[] words = input.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1)).append(" ");
            }
        }
        return capitalized.toString().trim();
    }

    public List<BreadcrumbItem> getItems() {
        return items;
    }
}