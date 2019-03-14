package org.syscolabs.cx.pos.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveOrderItemRequest {
    @NotNull(message = "Order Id cannot be null")
    @NotBlank(message = "Order Id cannot be blank")
    private String orderId;

    @NotNull(message = "Item Id cannot be null")
    @NotBlank(message = "Item Id cannot be blank")
    private String itemId;
}
