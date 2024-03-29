package org.syscolabs.cx.pos.dto.model;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    @Id
    private ObjectId _id;
    private double total_amount;
    private List<OrderItem> itemList;
    private String status;
    public String orderId;
    private String orderDate;
    private String orderTime;



    public Order() {
        this.itemList = new ArrayList<>();
    }
}
