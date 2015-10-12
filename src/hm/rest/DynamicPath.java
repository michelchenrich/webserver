package hm.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DynamicPath implements RestService {
    private final Pattern pathPattern;
    private final RestService handler;
    private final List<String> fields;

    public DynamicPath(String pathPattern, List<String> fields, RestService handler) {
        this.pathPattern = Pattern.compile(pathPattern);
        this.fields = fields;
        this.handler = handler;
    }

    public boolean matches(String path) {
        return pathPattern.matcher(path).matches();
    }

    public RestService getHandler() {
        return this;
    }

    public void run(Request request, Response response) throws Exception {
        handler.run(decorateRequest(request), response);
    }

    private Request decorateRequest(Request request) {
        return new DynamicRequest(request, makePathParameters(request));
    }

    private Map<String, String> makePathParameters(Request request) {
        Map<String, String> pathParameters = new HashMap<>();
        Matcher matcher = pathPattern.matcher(request.getPath());
        matcher.find();
        for (int i = 1; i <= matcher.groupCount(); i++)
            pathParameters.put(fields.get(i - 1), matcher.group(i));
        return pathParameters;
    }
}