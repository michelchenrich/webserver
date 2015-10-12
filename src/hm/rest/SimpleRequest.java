package hm.rest;

import java.util.Map;

class SimpleRequest implements Request {
    private final String path;
    private final Method method;
    private final Map<String, String> queryParameters;
    private final Map<String, String> formParameters;
    private final Map<String, String> headerFields;
    private final String body;

    public SimpleRequest(String path, Method method, Map<String, String> queryParameters, Map<String, String> formParameters, Map<String, String> headerFields, String body) {
        this.path = path;
        this.method = method;
        this.queryParameters = queryParameters;
        this.formParameters = formParameters;
        this.headerFields = headerFields;
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getHeaderField(String name) {
        return getValueInMap(name, headerFields);
    }

    public String getPathParameter(String name) {
        throw new NameNotFoundException(name);
    }

    public String getQueryParameter(String name) {
        return getValueInMap(name, queryParameters);
    }

    public String getFormParameter(String name) {
        return getValueInMap(name, formParameters);
    }

    private String getValueInMap(String name, Map<String, String> map) {
        if (map.containsKey(name)) return map.get(name);
        else throw new NameNotFoundException(name);
    }
}