package hm.rest;

import static hm.rest.ResponseStatus.NOT_FOUND;
import static java.lang.String.format;

class DefaultService implements RestService {
    public void run(Request request, Response response) {
        response.setStatus(NOT_FOUND);
        response.setContent(formatMessage(request));
    }

    private String formatMessage(Request request) {
        return format("No handler found for %s %s", request.getMethod(), request.getPath());
    }
}