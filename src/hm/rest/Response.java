package hm.rest;

import java.io.OutputStream;

public interface Response {
    void setStatus(ResponseStatus status);

    void setHeaderField(String name, String value);

    void setContent(String content);

    void setContent(Object content);

    void setContentProvider(ResponseContentProvider contentWriter);

    void writeToOutput(OutputStream outputStream);
}