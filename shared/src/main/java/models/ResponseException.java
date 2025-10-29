package models;

public class ResponseException extends Exception {
    private final int code;

    public ResponseException(int code) {
        super(defaultMessageFor(code)); // automatically sets message
        this.code = code;
    }

    public ResponseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static String defaultMessageFor(int code) {
        return switch (code) {
            case 400 -> "Error: bad request";
            case 401 -> "Error: unauthorized";
            case 403 -> "Error: already taken";
            case 404 -> "Error: not found";
            case 500 -> "Error: internal server error";
            default -> "Error: unknown error";
        };
    }
}