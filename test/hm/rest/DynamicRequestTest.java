package hm.rest;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DynamicRequestTest extends RequestTest {
    private Map<String, String> pathParameters = new HashMap<>();

    public void givenPathParameter(String name, String value) {
        pathParameters.put(name, value);
    }

    public void whenBuildingRequestObject() {
        super.whenBuildingRequestObject();
        request = new DynamicRequest(request, pathParameters);
    }

    @Test
    public void getPathParameters() {
        givenPathParameter("resource", "this.file");
        givenPathParameter("entity", "customerrest");
        whenBuildingRequestObject();
        assertEquals("this.file", request.getPathParameter("resource"));
        assertEquals("customerrest", request.getPathParameter("entity"));
    }

    @Test(expected = NameNotFoundException.class)
    public void exceptionIfPathParameterDoesNotExist(){
        whenBuildingRequestObject();
        request.getPathParameter("any");
    }
}
