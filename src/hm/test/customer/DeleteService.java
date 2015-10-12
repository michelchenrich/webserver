package hm.test.customer;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.RestService;
import webstore.DeleteCustomerUseCase;

public class DeleteService implements RestService {
    public void run(Request request, Response response) throws Exception {
        Receiver receiver = new Receiver();
        new DeleteCustomerUseCase(Context.customerGateway, new Input(request)).execute();
        receiver.sendChangeResponse(response);
    }
}
