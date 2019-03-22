package org.syscolabs.cx.pos.service;

import org.bson.types.ObjectId;
import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;

import java.util.List;

public interface OrderService {
    ObjectId addNewItem(OrderItem orderItem);

    ObjectId updateItemQty(OrderItem orderItem);

    ObjectId removeItemFromOrder(OrderItem orderItem);

    List<Order> getAllOrderList();

}
