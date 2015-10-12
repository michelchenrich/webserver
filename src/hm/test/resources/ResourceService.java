package hm.test.resources;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.ResponseStatus;
import hm.rest.RestService;
import static java.io.File.separator;

import java.io.File;

public class ResourceService implements RestService {
    private final RestService defaultService;
    private final String resourceDirectory;

    public ResourceService(RestService defaultService) {
        this(System.getProperty("user.dir") + separator + "resources", defaultService);
    }

    public ResourceService(String resourceDirectory, RestService defaultService) {
        this.defaultService = defaultService;
        this.resourceDirectory = resourceDirectory;
    }

    public void run(Request request, Response response) throws Exception {
        File file = new File(getFileName(request));
        if (file.exists()) {
            response.setStatus(ResponseStatus.OK);
            response.setHeaderField("Content-Type", getFileType(file));
            response.setContentProvider(new FileContentProvider(file));
        } else
            defaultService.run(request, response);
    }

    private String getFileName(Request request) {
        return resourceDirectory + separator + request.getPathParameter("filename");
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        if (fileName.endsWith(".html")) return "text/html";
        else if (fileName.endsWith(".css")) return "text/css";
        else if (fileName.endsWith(".js")) return "text/javascript";
        else return "text/plain";
    }
}