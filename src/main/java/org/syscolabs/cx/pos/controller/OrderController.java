package org.syscolabs.cx.pos.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.syscolabs.cx.pos.dto.model.OrderItem;
import org.syscolabs.cx.pos.dto.request.AddNewItemToOrderRequest;
import org.syscolabs.cx.pos.dto.request.RemoveOrderItemRequest;
import org.syscolabs.cx.pos.dto.request.UpdateOrderItemQtyRequest;
import org.syscolabs.cx.pos.dto.response.AddNewSuccessResponse;
import org.syscolabs.cx.pos.dto.response.RemoveSuccessResponse;
import org.syscolabs.cx.pos.dto.response.UpdateSuccessResponse;
import org.syscolabs.cx.pos.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping(value = "/add/item", produces = {"application/hal+json"})
    public AddNewSuccessResponse addNewItemToOrder(@Validated AddNewItemToOrderRequest addNewItemToOrderRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(addNewItemToOrderRequest.getItemId());
        orderItem.setOrderId(addNewItemToOrderRequest.getOrderId());
        orderItem.setQty(addNewItemToOrderRequest.getQty());
        orderItem.setUnit_price(addNewItemToOrderRequest.getUnitPrice());
        ObjectId objectId = orderService.addNewItem(orderItem);
        System.out.println(objectId.toHexString());
        return new AddNewSuccessResponse(objectId.toHexString(), OrderItem.class.getSimpleName());
    }

    @PutMapping(value = "/update/item/qty", produces = {"application/hal+json"})
    public UpdateSuccessResponse updateOrderItemQty(@Validated UpdateOrderItemQtyRequest updateOrderItemQtyRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(updateOrderItemQtyRequest.getItemId());
        orderItem.setOrderId(updateOrderItemQtyRequest.getOrderId());
        orderItem.setQty(updateOrderItemQtyRequest.getQty());

        return new UpdateSuccessResponse(orderService.updateItemQty(orderItem).toHexString(),
                OrderItem.class.getSimpleName());
    }

    @DeleteMapping(value = "remove/item")
    public RemoveSuccessResponse removeItemFromOrder(@Validated RemoveOrderItemRequest removeOrderItemRequest) {
        ObjectId objectId = orderService.removeItemFromOrder(
                new OrderItem(removeOrderItemRequest.getOrderId(), removeOrderItemRequest.getItemId())
        );

        return new RemoveSuccessResponse(objectId.toHexString());
    }


}
