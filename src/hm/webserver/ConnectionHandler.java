package hm.webserver;

import java.io.InputStream;
import java.io.OutputStream;

public interface ConnectionHandler {
    void run(InputStream inputStream, OutputStream outputStream);
}