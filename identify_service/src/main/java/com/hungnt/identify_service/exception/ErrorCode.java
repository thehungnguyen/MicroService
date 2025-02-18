package com.hungnt.identify_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(2, "User Existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(3, "Username must be at least 5 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(4, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOTFOUND(5, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(6, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(7, "Access Denied", HttpStatus.FORBIDDEN),
    ROLE_NOTFOUND(8, "Role not found", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(9, "Email Existed", HttpStatus.BAD_REQUEST),
    IO_EXCEPTION(10, "I/O error", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_NOTFOUND(11, "Product not found", HttpStatus.NOT_FOUND)
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
