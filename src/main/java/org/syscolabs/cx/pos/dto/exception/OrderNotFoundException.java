package org.syscolabs.cx.pos.dto.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderId) {
        super("Order Not Found for OrderID : " + orderId);
    }
}
