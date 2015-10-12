package hm.rest;

public class NameNotFoundException extends RuntimeException {
    private String name;

    public NameNotFoundException(String name) {
        this.name = name;
    }

    public String getMessage() {
        return String.format("Parameter '%s' was not found in the current request", name);
    }
}