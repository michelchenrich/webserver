package hm.test.customer;

import hm.rest.Response;
import static hm.rest.ResponseStatus.BAD_REQUEST;
import static hm.rest.ResponseStatus.OK;
import webstore.CreateCustomerReceiver;
import webstore.PresentableCustomer;
import webstore.ReadCustomerReceiver;
import webstore.UpdateCustomerReceiver;

import java.util.ArrayList;
import java.util.List;

class Receiver implements CreateCustomerReceiver, ReadCustomerReceiver, UpdateCustomerReceiver {
    private List<InputMessage> messages = new ArrayList<>();
    private List<PresentableCustomer> customers = new ArrayList<>();

    public void sendId(String id) {
    }

    public void sendFirstNameIsEmpty() {
        messages.add(new InputMessage("firstName", "First name must be filled"));
    }

    public void sendLastNameIsEmpty() {
        messages.add(new InputMessage("lastName", "Last name must be filled"));
    }

    public void sendCustomer(PresentableCustomer customer) {
        customers.add(customer);
    }

    public void sendIdIsInvalid(String id) {
        messages.add(new InputMessage("id", "Customer ID " + id + " does not exist"));
    }

    public void sendChangeResponse(Response response) {
        if (messages.isEmpty()) {
            response.setStatus(OK);
            response.setContent("{}");
        } else {
            response.setStatus(BAD_REQUEST);
            response.setContent(messages);
        }
    }

    public void sendSingleRead(Response response) {
        if (messages.isEmpty()) {
            response.setStatus(OK);
            response.setContent(customers.get(0));
        } else {
            response.setStatus(BAD_REQUEST);
            response.setContent(messages);
        }
    }

    public void sendMultipleReads(Response response) {
        response.setStatus(OK);
        response.setContent(customers);
    }
}
