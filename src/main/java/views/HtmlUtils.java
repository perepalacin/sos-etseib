package views;

public class HtmlUtils {
    public static String escapeHtml(String s) {
        if (s == null) {
            return null;
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
