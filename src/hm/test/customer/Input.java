package hm.test.customer;

import hm.rest.Request;
import webstore.ChangeCustomerInput;
import webstore.DeleteCustomerInput;
import webstore.ReadCustomerInput;
import webstore.UpdateCustomerInput;

class Input implements ChangeCustomerInput, DeleteCustomerInput, UpdateCustomerInput, ReadCustomerInput {
    private final Request request;

    public Input(Request request) {
        this.request = request;
    }

    public String getFirstName() {
        return request.getFormParameter("firstName");
    }

    public String getLastName() {
        return request.getFormParameter("lastName");
    }

    public String getId() {
        return request.getPathParameter("id");
    }
}
