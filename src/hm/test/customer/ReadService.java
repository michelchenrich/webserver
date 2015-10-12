package hm.test.customer;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.RestService;
import webstore.ReadCustomerUseCase;

public class ReadService implements RestService {
    public void run(Request request, Response response) throws Exception {
        Receiver receiver = new Receiver();
        new ReadCustomerUseCase(Context.customerGateway, receiver, new Input(request)).execute();
        receiver.sendSingleRead(response);
    }
}