package org.syscolabs.cx.pos.dto.response;

import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.enums.ResponseStatus;

public class RemoveSuccessResponse extends BasicResponseWrapper<UpdateContent> {
    public RemoveSuccessResponse(String orderId) {
        super(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, new UpdateContent(orderId, Order.class.getSimpleName()));
    }
}


