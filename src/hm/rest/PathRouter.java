package hm.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PathRouter {
    private Map<String, RestService> services = new HashMap<>();
    private List<DynamicPath> dynamicPaths = new ArrayList<>();
    private RestService defaultService;

    public PathRouter(RestService defaultService) {
        this.defaultService = defaultService;
    }

    public void register(String path, RestService handler) {
        if (isDynamic(path)) dynamicPaths.add(DynamicPathFactory.make(path, handler));
        else services.put(path, handler);
    }

    private boolean isDynamic(String path) {
        return path.contains("{");
    }

    public RestService getService(String path) {
        if (services.containsKey(path))
            return services.get(path);
        else
            for (DynamicPath dynamicPath : dynamicPaths)
                if (dynamicPath.matches(path)) return dynamicPath.getHandler();
        return defaultService;
    }

    public void setDefaultService(RestService defaultService) {
        this.defaultService = defaultService;
    }
}