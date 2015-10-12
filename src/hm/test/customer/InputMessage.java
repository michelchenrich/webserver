package hm.test.customer;

public class InputMessage {
    public String field;
    public String type;
    public String text;

    public InputMessage(String field, String text) {
        this.field = field;
        this.text = text;
        this.type = "error";
    }

    public InputMessage(String field, String type, String text) {
        this.field = field;
        this.text = text;
        this.type = type;
    }
}
