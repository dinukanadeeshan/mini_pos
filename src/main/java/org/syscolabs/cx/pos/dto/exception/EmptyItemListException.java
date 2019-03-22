package org.syscolabs.cx.pos.dto.exception;

public class EmptyItemListException extends RuntimeException {
    public EmptyItemListException(String orderId) {
        super("Empty item list for order with order id, " + orderId);
    }
}
