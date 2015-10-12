package hm.rest;

import com.google.gson.Gson;

import java.nio.charset.Charset;

class JSONContentProvider extends SimpleContentProvider {
    private final Gson gson = new Gson();
    private final Object content;
    private final Charset charset;

    public JSONContentProvider(Object content, Charset charset) {
        this.content = content;
        this.charset = charset;
    }

    protected byte[] makeContent() {
        return gson.toJson(content).getBytes(charset);
    }
}