package org.syscolabs.cx.pos.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscolabs.cx.pos.dto.exception.OrderNotFoundException;
import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.repository.OrderRepository;
import org.syscolabs.cx.pos.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public ObjectId addNewItem(OrderItem orderItem) {
        Order order = orderRepository.findBy_id(orderItem.getOrderId());
        if (order == null) {
            throw new OrderNotFoundException(orderItem.getOrderId());
        }

        if (order.getItemList() == null) order.setItemList(new ArrayList<>());

        order.getItemList().add(orderItem);
        return orderRepository.save(order).get_id();
    }

    @Override
    public ObjectId updateItemQty(OrderItem orderItem) {
        Order order = orderRepository.findBy_id(orderItem.getOrderId());
        List<OrderItem> itemList = order.getItemList();
        itemList.forEach(item -> {
            if (item.getItemId().equals(orderItem.getItemId())) {
                item.setQty(orderItem.getQty());
            }
        });

        return orderRepository.save(order).get_id();
    }
}
