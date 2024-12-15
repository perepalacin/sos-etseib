package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BreadcrumbsDao {
    public List<String> crumbs;
    public List<String> routes;

    public BreadcrumbsDao(List<String> routesArray) {
        this.routes = new ArrayList<String>();
        crumbs = new ArrayList<String>();
        this.generateCrumps(routesArray);
    }

    private void generateCrumps (List<String> routes) {
        StringBuilder path = new StringBuilder("/files");
        routes.forEach(route -> {
            path.append("/").append(route);
            String[] words = route.split(" ");
            StringBuilder crump = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    crump.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.substring(1))
                            .append(" ");
                }
            }
            String formattedRoute = crump.toString().trim();
            System.out.println(formattedRoute);
            System.out.println(path);
            this.crumbs.add(formattedRoute);
            this.routes.add(String.valueOf(path));
        });
    }

}
