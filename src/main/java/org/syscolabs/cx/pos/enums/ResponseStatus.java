package org.syscolabs.cx.pos.enums;

public enum ResponseStatus {
    SUCCESS(200),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    CLIENT_ERROR(400),
    AUTHENTICATION_ERROR(401);

    int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
