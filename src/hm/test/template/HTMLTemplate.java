package hm.test.template;

import static java.io.File.separator;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HTMLTemplate {
    private static final String directory = System.getProperty("user.dir") + separator + "templates";
    private Map<String, String> replacements = new HashMap<>();
    private String template;

    public static HTMLTemplate fromFile(File file) {
        return HTMLTemplate.fromFileName(file.getName());
    }

    public static HTMLTemplate fromFileName(String fileName) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(directory + separator + fileName));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return new HTMLTemplate(new String(bytes, StandardCharsets.ISO_8859_1));
    }

    private HTMLTemplate(String template) {
        this.template = template;
    }

    public void put(String placeholder, String replacement) {
        replacements.put(placeholder, replacement);
    }

    public void putAll(Map<String, String> replacements) {
        this.replacements.putAll(replacements);
    }

    public String toHTML() {
        for (Entry<String, String> r : replacements.entrySet())
            template = template.replace(toPlaceholder(r.getKey()), r.getValue());
        return template;
    }

    private String toPlaceholder(String key) {
        return format("${%s}", key);
    }
}
