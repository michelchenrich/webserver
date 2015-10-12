package hm.rest;

public interface Request {
    String getPath();

    Method getMethod();

    String getBody();

    String getHeaderField(String name);

    String getPathParameter(String name);

    String getQueryParameter(String name);

    String getFormParameter(String name);
}