package hm.test.customer;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.RestService;
import webstore.ReadCustomersUseCase;

public class ReadAllService implements RestService {
    public void run(Request request, Response response) throws Exception {
        Receiver receiver = new Receiver();
        new ReadCustomersUseCase(Context.customerGateway, receiver).execute();
        receiver.sendMultipleReads(response);
    }
}
