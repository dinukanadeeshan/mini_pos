package org.syscolabs.cx.pos.dto.exception;

public class NoItemFoundInOrder extends RuntimeException {
    public NoItemFoundInOrder(String orderId, String itemId) {
        super("Item with id " + itemId + " not found in order with id " + orderId);
    }
}
