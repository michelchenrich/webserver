package hm.rest;

import static hm.rest.ResponseStatus.OK;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class RestHandlerTest {
    private ByteArrayOutputStream outputStream;
    private RestHandler handler;
    private RandomContentRestService fakeHandler;

    @Before
    public void setUp() throws Exception {
        handler = new RestHandler();
        fakeHandler = new RandomContentRestService();
    }

    @Test
    public void unregisteredPathShouldCallDefaultHandler() throws Exception {
        givenRegistration("GET", "/", new RandomContentRestService());
        givenDefaultService(fakeHandler);
        whenRunningWith("GET /unregistered/path?with=some&query=parameters HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void withNoRegisteredPathsShouldCallDefaultHandler() throws Exception {
        givenDefaultService(fakeHandler);
        whenRunningWith("GET /unregistered/path?with=some&query=parameters HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void simplePathRouting() throws Exception {
        givenRegistration("GET", "/", fakeHandler);
        whenRunningWith("GET / HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void simplePathRoutingButWithAnExtraSlash() throws Exception {
        givenRegistration("GET", "/test/", fakeHandler);
        whenRunningWith("GET /test HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void routingWithSamePathButDifferentMethods() throws Exception {
        givenRegistration("GET", "/", new RandomContentRestService());
        givenRegistration("PUT", "/", fakeHandler);
        whenRunningWith("PUT / HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void dynamicPathRouting() throws Exception {
        givenRegistration("GET", "/{id}", fakeHandler);
        whenRunningWith("GET /1 HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void dynamicPathWithQueryParameters() throws Exception {
        givenRegistration("GET", "/{id}", fakeHandler);
        whenRunningWith("GET /1?some=query HTTP/1.1");
        assertHandlerWasCalled();
    }

    @Test
    public void routingWithSameDynamicPathsButDifferentMethods() throws Exception {
        givenRegistration("GET", "/{id}", new RandomContentRestService());
        givenRegistration("PUT", "/{id}", fakeHandler);
        whenRunningWith("PUT /1 HTTP/1.1");
        assertHandlerWasCalled();
    }

    private void givenDefaultService(RandomContentRestService fakeService) {
        handler.setDefaultService(fakeService);
    }

    private void givenRegistration(String method, String path, RestService handler) {
        this.handler.register(Method.valueOf(method), path, handler);
    }

    private void whenRunningWith(String request) throws Exception {
        request += "\r\nHost: localhost\r\n\r\n";
        outputStream = new ByteArrayOutputStream();
        handler.run(new ByteArrayInputStream(request.getBytes()), outputStream);
    }

    private void assertHandlerWasCalled() {
        String response = "HTTP/1.1 200 OK\r\n" +
                String.format("Content-Length: %d\r\n", fakeHandler.content.getBytes().length) +
                "\r\n" +
                fakeHandler.content;
        assertEquals(response, outputStream.toString());
    }

    private static class RandomContentRestService implements RestService {
        private static int instanceCounter = 0;
        private static final double seed = Math.random();
        public String content;

        public RandomContentRestService() {
            instanceCounter++;
            content = String.valueOf(seed * instanceCounter);
        }

        public void run(Request request, Response response) {
            response.setStatus(OK);
            response.setContent(content);
        }
    }
}