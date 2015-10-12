package hm.test;

import hm.rest.RestHandler;
import hm.test.customer.*;
import hm.test.resources.ResourceService;
import hm.test.template.TemplateService;
import hm.webserver.Servers;
import webstore.InMemoryCustomerGateway;

import java.net.UnknownHostException;

import static hm.rest.Method.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Servers Servers = new Servers();
        Servers.add(4567, getStaticResourcesHandler());
        Servers.add(45678, getCustomerHandler());
        Servers.execute();
    }

    private static RestHandler getStaticResourcesHandler() {
        RestHandler restHandler = new RestHandler();
        restHandler.register(GET, "/resources/{filename}", new ResourceService(restHandler.getDefaultService()));
        restHandler.register(GET, "/templates/helloworld", new TemplateService());
        return restHandler;
    }

    private static RestHandler getCustomerHandler() throws UnknownHostException {
        Context.customerGateway = new InMemoryCustomerGateway();

        RestHandler restHandler = new RestHandler();
        restHandler.register(POST, "/customer", new CreateService());
        restHandler.register(GET, "/customer/all", new ReadAllService());
        restHandler.register(GET, "/customer/{id}", new ReadService());
        restHandler.register(PUT, "/customer/{id}", new UpdateService());
        restHandler.register(DELETE, "/customer/{id}", new DeleteService());
        return restHandler;
    }
}