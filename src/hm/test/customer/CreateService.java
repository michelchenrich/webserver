package hm.test.customer;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.RestService;
import webstore.CreateCustomerUseCase;

public class CreateService implements RestService {
    public void run(Request request, Response response) throws Exception {
        Receiver receiver = new Receiver();
        new CreateCustomerUseCase(Context.customerGateway, receiver, new Input(request)).execute();
        receiver.sendChangeResponse(response);
    }
}
