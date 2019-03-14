package org.syscolabs.cx.pos.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscolabs.cx.pos.dto.exception.EmptyItemListException;
import org.syscolabs.cx.pos.dto.exception.NoItemFoundInOrder;
import org.syscolabs.cx.pos.dto.exception.OrderNotFoundException;
import org.syscolabs.cx.pos.dto.exception.NullOrderListException;
import org.syscolabs.cx.pos.dto.model.Order;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.repository.OrderRepository;
import org.syscolabs.cx.pos.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public ObjectId addNewItem(OrderItem orderItem) {
        Order order = orderRepository.findBy_id(orderItem.getOrderId());

        if (order == null) throw new OrderNotFoundException(orderItem.getOrderId());

        if (order.getItemList() == null) order.setItemList(new ArrayList<>());

        order.getItemList().add(orderItem);
        return orderRepository.save(order).get_id();
    }

    @Override
    public ObjectId updateItemQty(OrderItem orderItem) {
        Order order = orderRepository.findBy_id(orderItem.getOrderId());

        if (order == null) throw new OrderNotFoundException(orderItem.getOrderId());

        List<OrderItem> itemList = order.getItemList();

        if (itemList == null || itemList.isEmpty()) throw new EmptyItemListException(orderItem.getOrderId());

        if (itemList.stream().noneMatch(item -> item.getItemId().equals(orderItem.getItemId())))
            throw new NoItemFoundInOrder(orderItem.getOrderId(), orderItem.getItemId());

        itemList.forEach(item -> {
            if (item.getItemId().equals(orderItem.getItemId())) {
                item.setQty(orderItem.getQty());
            }
        });

        return orderRepository.save(order).get_id();
    }

    @Override
    public ObjectId removeItemFromOrder(OrderItem orderItem) {
        Order order = orderRepository.findBy_id(orderItem.getOrderId());
        if (order == null) throw new OrderNotFoundException(orderItem.getOrderId());

        List<OrderItem> itemList = order.getItemList();

        if (itemList == null || itemList.isEmpty()) throw new EmptyItemListException(orderItem.getOrderId());

        if (!itemList.stream().anyMatch(item -> item.getItemId().equals(orderItem.getItemId())))
            throw new NoItemFoundInOrder(orderItem.getOrderId(), orderItem.getItemId());

        itemList = itemList
                .stream().filter(item -> !item.getItemId().equals(orderItem.getItemId())).collect(Collectors.toList());

        order.setItemList(itemList);

        return orderRepository.save(order).get_id();
    }

    @Override
    public List<Order> getAllOrderList() {
        List<Order> orderList = orderRepository.findAll();

        if (orderList == null) throw new NullOrderListException();

        orderList.stream().forEach(order -> {
            order.setOrderId(order.get_id().toHexString());
            order.set_id(null);
        });

        return orderList;
    }
}
