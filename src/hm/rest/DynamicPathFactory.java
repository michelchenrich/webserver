package hm.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DynamicPathFactory {
    private static final Pattern FIELD_PATTERN = Pattern.compile("\\{([^}]*)\\}");

    public static DynamicPath make(String path, RestService handler) {
        return new DynamicPath(toPattern(path), findFields(path), handler);
    }

    private static String toPattern(String path) {
        return path.replace(".", "\\.").replaceAll("(\\{.*?\\})", "(.+)");
    }

    private static List<String> findFields(String path) {
        List<String> fields = new ArrayList<>();
        Matcher matcher = FIELD_PATTERN.matcher(path);
        while (matcher.find()) fields.add(formatField(matcher.group()));
        return fields;
    }

    private static String formatField(String field) {
        return field.replace("{", "").replace("}", "");
    }
}