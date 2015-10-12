package hm.rest;

import static hm.rest.Method.GET;
import static hm.rest.Method.POST;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public abstract class RequestTest {
    protected Request request;
    private String headerLine = "";
    private String headerFields = "";
    private String body = "";

    private void givenHeaderLine(Method method, String path) {
        headerLine = format("%s %s HTTP/1.1\r\n", method.toString(), path);
    }

    private void givenHeaderField(String name, String value) {
        headerFields += format("%s: %s\r\n", name, value);
    }

    private void givenBody(String body) {
        givenHeaderField("Content-Length", valueOf(body.getBytes().length));
        this.body = body;
    }

    public void whenBuildingRequestObject() {
        String requestString = headerLine + headerFields + "\r\n" + body;
        request = new SimpleRequestBuilder(new ByteArrayInputStream(requestString.getBytes())).build();
    }

    @Before
    public void setUp() {
        givenHeaderLine(GET, "/");
    }

    @Test
    public void keepSlashIfOnlyOne() {
        givenHeaderLine(GET, "/");
        whenBuildingRequestObject();
        assertEquals("/", request.getPath());
    }

    @Test
    public void removeExtraSlash() {
        givenHeaderLine(GET, "/url/with/extra/slash/");
        whenBuildingRequestObject();
        assertEquals("/url/with/extra/slash", request.getPath());
    }

    @Test
    public void methods() {
        givenHeaderLine(GET, "/");
        whenBuildingRequestObject();
        assertEquals(GET, request.getMethod());

        givenHeaderLine(POST, "/");
        whenBuildingRequestObject();
        assertEquals(POST, request.getMethod());
    }

    @Test
    public void getExistentHeaderFields() {
        givenHeaderField("Host", "localhost");
        givenHeaderField("Header-Field1", "Value1");
        givenHeaderField("Header-Field2", "Value2");
        whenBuildingRequestObject();
        assertEquals("localhost", request.getHeaderField("Host"));
        assertEquals("Value1", request.getHeaderField("Header-Field1"));
        assertEquals("Value2", request.getHeaderField("Header-Field2"));
    }

    @Test(expected = NameNotFoundException.class)
    public void exceptionIfHeaderFieldDoesNotExist() {
        whenBuildingRequestObject();
        request.getHeaderField("Non-existent-field-1");
    }

    @Test
    public void getQueryParameters() {
        givenHeaderLine(GET, "/someUrl?Query-Parameter1=Value1&Query-Parameter2=Value2");
        whenBuildingRequestObject();
        assertEquals("Value1", request.getQueryParameter("Query-Parameter1"));
        assertEquals("Value2", request.getQueryParameter("Query-Parameter2"));
    }

    @Test(expected = NameNotFoundException.class)
    public void exceptionIfQueryParameterDoesNotExist1() {
        whenBuildingRequestObject();
        request.getQueryParameter("Non-existent-parameter-1");
    }

    @Test
    public void getBodies() {
        givenBody("{body: of,\r\nthe: request_1}");
        whenBuildingRequestObject();
        assertEquals("{body: of,\r\nthe: request_1}", request.getBody());

        givenBody("{body: of,\r\nthe: request_2}");
        whenBuildingRequestObject();
        assertEquals("{body: of,\r\nthe: request_2}", request.getBody());
    }

    @Test
    public void getEmptyBodyWhenThereIsNone() {
        whenBuildingRequestObject();
        assertEquals("", request.getBody());
    }
}
