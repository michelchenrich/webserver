package hm.test.customer;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.RestService;
import webstore.UpdateCustomerUseCase;

public class UpdateService implements RestService {
    public void run(Request request, Response response) throws Exception {
        Receiver receiver = new Receiver();
        new UpdateCustomerUseCase(receiver, Context.customerGateway, new Input(request)).execute();
        receiver.sendChangeResponse(response);
    }
}
