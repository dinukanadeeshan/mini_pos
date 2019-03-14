package org.syscolabs.cx.pos.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private ObjectId _id;
    private String itemId;
    private String name;
    private double unit_price;
    private int qtyOnStock;
    private String status;
}
