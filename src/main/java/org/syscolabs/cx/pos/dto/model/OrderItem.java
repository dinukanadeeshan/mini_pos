package org.syscolabs.cx.pos.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private double unit_price;
    private int qty;
    private String orderId;
    private String itemId;
    private String itemName;

    public OrderItem(String orderId, String itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }
}
