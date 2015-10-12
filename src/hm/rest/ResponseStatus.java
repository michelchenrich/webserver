package hm.rest;

public enum ResponseStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect");

    private final int code;
    private final String message;

    private ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toString() {
        return String.format("%d %s", code, message);
    }
}