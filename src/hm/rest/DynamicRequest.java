package hm.rest;

import java.util.Map;

class DynamicRequest implements Request {
    private Request request;
    private Map<String, String> pathParameters;

    public DynamicRequest(Request request, Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
        this.request = request;
    }

    public String getPath() {
        return request.getPath();
    }

    public Method getMethod() {
        return request.getMethod();
    }

    public String getBody() {
        return request.getBody();
    }

    public String getHeaderField(String name) {
        return request.getHeaderField(name);
    }

    public String getPathParameter(String name) {
        if (pathParameters.containsKey(name)) return pathParameters.get(name);
        else throw new NameNotFoundException(name);
    }

    public String getQueryParameter(String name) {
        return request.getQueryParameter(name);
    }

    public String getFormParameter(String name) {
        return request.getFormParameter(name);
    }
}