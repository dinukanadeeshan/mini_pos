package org.syscolabs.cx.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.syscolabs.cx.pos.dto.model.Item;
import org.syscolabs.cx.pos.dto.response.GetAllSuccessRespone;
import org.syscolabs.cx.pos.service.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/", produces = {"application/hal+json"})
    public GetAllSuccessRespone<Item> getAllItems() {
        return new GetAllSuccessRespone<>(itemService.getAllItems(), Item.class.getSimpleName());
    }

}
