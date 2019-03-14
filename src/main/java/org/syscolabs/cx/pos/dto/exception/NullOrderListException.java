package org.syscolabs.cx.pos.dto.exception;

public class NullOrderListException extends RuntimeException {
    public NullOrderListException() {
        super("Order list is null");
    }
}
