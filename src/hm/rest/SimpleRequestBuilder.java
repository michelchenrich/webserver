package hm.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class SimpleRequestBuilder {
    private BufferedReader reader;
    private String path;
    private Method method;
    private Map<String, String> headerFields = new HashMap<>();
    private Map<String, String> queryParameters = new HashMap<>();
    private Map<String, String> formParameters = new HashMap<>();
    private String body = "";
    private String currentLine;

    public SimpleRequestBuilder(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public Request build() {
        try {
            readRequestLine();
            readHeaderFields();
            readBody();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return new SimpleRequest(path, method, queryParameters, formParameters, headerFields, body);
    }

    private void readRequestLine() throws IOException {
        if (!hasNextLine()) throw new NullRequestException();

        System.out.println(currentLine + "\r\n-------------------------------------");

        String[] words = currentLine.split("\\s", 3);
        method = Method.valueOf(words[0]);

        String pathParts[] = words[1].split("\\?", 2);
        readPath(pathParts[0]);
        if (pathParts.length > 1)
            readQueryParameters(pathParts[1]);
    }

    private void readPath(String pathPart) {
        path = PathUtilities.removeExtraSlash(pathPart);
    }

    private void readQueryParameters(String query) {
        for (String queryPart : query.split("&")) readQueryParameter(queryPart);
    }

    private void readQueryParameter(String queryPart) {
        String[] queryParameterParts = queryPart.split("=", 2);
        queryParameters.put(queryParameterParts[0].trim(), queryParameterParts[1].trim());
    }

    private void readHeaderFields() throws IOException {
        while (hasNextLine()) readHeaderField(currentLine);
    }

    private void readHeaderField(String line) {
        String[] lineParts = line.split(":", 2);
        headerFields.put(lineParts[0].trim(), lineParts[1].trim());
    }

    private void readBody() throws IOException {
        if (!hasBody()) return;
        int length = Integer.parseInt(headerFields.get("Content-Length"));
        char[] bodyCharacters = new char[length];
        reader.read(bodyCharacters);
        parseBodyContent(new String(bodyCharacters));
    }

    private void parseBodyContent(String bodyContent) {
        if (headerFields.get("Content-Type").contains("application/x-www-form-urlencoded")) {
            for (String formPart : bodyContent.split("&")) {
                String[] parameterParts = formPart.split("=", 2);
                System.out.println(String.format("%s: %s", parameterParts[0], parameterParts[1]));
                formParameters.put(parameterParts[0], parameterParts[1]);
            }
        } else {
            body = bodyContent;
            System.out.println(body);
        }
    }

    private boolean hasBody() {
        return headerFields.containsKey("Content-Length");
    }

    private boolean hasNextLine() throws IOException {
        currentLine = reader.readLine();
        return currentLine != null && currentLine.length() > 0;
    }
}