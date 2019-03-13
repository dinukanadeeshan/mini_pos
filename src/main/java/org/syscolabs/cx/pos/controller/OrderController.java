package org.syscolabs.cx.pos.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.dto.request.AddNewItemToOrderRequest;
import org.syscolabs.cx.pos.dto.request.UpdateOrderItemQtyRequest;
import org.syscolabs.cx.pos.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping(value = "/add/item", produces = {"application/hal+json"})
    public ObjectId addNewItemToOrder(@Validated AddNewItemToOrderRequest addNewItemToOrderRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(addNewItemToOrderRequest.getItemId());
        orderItem.setOrderId(addNewItemToOrderRequest.getOrderId());
        orderItem.setQty(addNewItemToOrderRequest.getQty());
        orderItem.setUnit_price(addNewItemToOrderRequest.getUnitPrice());
        ObjectId objectId = orderService.addNewItem(orderItem);
        return objectId;
    }

    @PutMapping(value = "/update/item/qty", produces = {"application/hal+json"})
    public ObjectId updateOrderItemQty(@Validated UpdateOrderItemQtyRequest updateOrderItemQtyRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(updateOrderItemQtyRequest.getItemId());
        orderItem.setOrderId(updateOrderItemQtyRequest.getOrderId());
        orderItem.setQty(updateOrderItemQtyRequest.getQty());

        return orderService.updateItemQty(orderItem);
    }

}
