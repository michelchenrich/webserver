package hm.test.template;

import hm.rest.Request;
import hm.rest.Response;
import hm.rest.ResponseStatus;
import hm.rest.RestService;

public class TemplateService implements RestService {
    public void run(Request request, Response response) throws Exception {
        HTMLTemplate createForm = HTMLTemplate.fromFileName("create_customer.html");
        createForm.put("title", "Create Customer");
        createForm.put("firstNameLabel", "First Name");
        createForm.put("lastNameLabel", "Last Name");
        createForm.put("birthDateLabel", "Birth Date");
        createForm.put("submitButtonLabel", "Create");

        HTMLTemplate layout = HTMLTemplate.fromFileName("layout.html");
        layout.put("title", "Create Customer");
        layout.put("content", createForm.toHTML());

        response.setStatus(ResponseStatus.OK);
        response.setHeaderField("Content-Type", "text/html");
        response.setContent(layout.toHTML());
    }
}
