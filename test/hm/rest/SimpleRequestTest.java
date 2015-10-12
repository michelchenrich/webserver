package hm.rest;

import org.junit.Test;

public class SimpleRequestTest extends RequestTest {
    @Test(expected = NameNotFoundException.class)
    public void exceptionWheneverPathParameterIsCalled1() {
        whenBuildingRequestObject();
        request.getPathParameter("some-path-parameter-1");
    }
}