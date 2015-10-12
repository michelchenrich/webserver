package hm.rest;

import static hm.rest.ResponseStatus.OK;
import static java.lang.String.format;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

class SimpleResponse implements Response {
    private Charset charset = StandardCharsets.ISO_8859_1;
    private Map<String, String> headerFields = new HashMap<>();
    private ResponseStatus status = OK;
    private ResponseContentProvider contentProvider = new NullContentProvider();

    public void setContent(String content) {
        this.contentProvider = new FixedContentProvider(content.getBytes(charset));
    }

    public void setContent(Object content) {
        this.contentProvider = new JSONContentProvider(content, charset);
    }

    public void setContentProvider(ResponseContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setHeaderField(String name, String value) {
        headerFields.put(name, value);
    }

    public String makeHeader() {
        return makeResponseLine() + makeResponseHeader() + "\r\n";
    }

    private String makeResponseLine() {
        return format("HTTP/1.1 %s\r\n", status);
    }

    private String makeResponseHeader() {
        String header = "";
        for (String name : headerFields.keySet()) header += format("%s: %s\r\n", name, headerFields.get(name));
        header += makeContentHeaders();
        return header;
    }

    private String makeContentHeaders() {
        return format("Content-Length: %d\r\n", contentProvider.getLength());
    }

    public void writeToOutput(OutputStream outputStream) {
        try {
            outputStream.write(makeHeader().getBytes(charset));
            outputStream.flush();
            while (contentProvider.hasNextBlock()) {
                outputStream.write(contentProvider.getNextBlock());
                outputStream.flush();
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}