package hm.rest;

import static hm.rest.ResponseStatus.INTERNAL_SERVER_ERROR;
import static hm.rest.ResponseStatus.OK;
import hm.webserver.ConnectionHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class RestHandler implements ConnectionHandler {
    private Router router = new Router();

    public void setDefaultService(RestService defaultService) {
        router.setDefaultService(defaultService);
    }

    public RestService getDefaultService() {
        return router.getDefaultService();
    }

    public void register(Method method, String path, RestService handler) {
        router.register(method, path, handler);
    }

    public void run(InputStream inputStream, OutputStream outputStream) {
        try {
            Request request = makeRequest(inputStream);

            Response response;
            if (request.getMethod() == Method.OPTIONS) {
                response = new SimpleResponse();
                response.setStatus(OK);
            } else {
                response = runService(request);
            }

            response.setHeaderField("Access-Control-Allow-Origin", "*");
            response.setHeaderField("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            response.setHeaderField("Access-Control-Allow-Credentials", "false");
            response.setHeaderField("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
            response.setHeaderField("Access-Control-Max-Age", "1209600");
            response.writeToOutput(outputStream);
        } catch (NullRequestException ignored) {
        }
    }

    private Request makeRequest(InputStream inputStream) {
        return new SimpleRequestBuilder(inputStream).build();
    }

    private Response runService(Request request) {
        try {
            Response response = new SimpleResponse();
            router.getService(request).run(request, response);
            return response;
        } catch (Exception exception) {
            return makeInternalErrorResponse(exception);
        }
    }

    private Response makeInternalErrorResponse(Exception exception) {
        Response response = new SimpleResponse();
        response.setStatus(INTERNAL_SERVER_ERROR);
        response.setContent(exception.toString());
        return response;
    }
}