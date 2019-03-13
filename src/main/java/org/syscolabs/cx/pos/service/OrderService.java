package org.syscolabs.cx.pos.service;

import org.bson.types.ObjectId;
import org.syscolabs.cx.pos.dto.model.OrderItem;

public interface OrderService {
    ObjectId addNewItem(OrderItem orderItem);

    ObjectId updateItemQty(OrderItem orderItem);
}
