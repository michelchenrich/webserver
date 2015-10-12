package hm.rest;

import java.util.EnumMap;
import java.util.Map;

class Router {
    private Map<Method, PathRouter> routers = new EnumMap<>(Method.class);
    private RestService defaultService = new DefaultService();

    public void register(Method method, String path, RestService handler) {
        if (!routers.containsKey(method))
            routers.put(method, new PathRouter(defaultService));
        routers.get(method).register(PathUtilities.removeExtraSlash(path), handler);
    }

    public RestService getService(Request request) {
        if (routers.containsKey(request.getMethod()))
            return routers.get(request.getMethod()).getService(request.getPath());
        else return defaultService;
    }

    public void setDefaultService(RestService defaultService) {
        this.defaultService = defaultService;
        for(PathRouter router : routers.values())
            router.setDefaultService(defaultService);
    }

    public RestService getDefaultService() {
        return defaultService;
    }
}