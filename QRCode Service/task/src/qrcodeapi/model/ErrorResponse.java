package qrcodeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ErrorResponse {
    // Annotation are hacks to overcome hyperskill.org tests validations
    @JsonProperty(value = "error")
    private final String message;
    @JsonIgnore
    private final HttpStatus errorCode;

    public ErrorResponse(String errorResponse, HttpStatus errorCode) {
        this.message = errorResponse;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}