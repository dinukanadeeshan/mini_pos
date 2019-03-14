package org.syscolabs.cx.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscolabs.cx.pos.dto.model.Item;
import org.syscolabs.cx.pos.repository.ItemRepository;
import org.syscolabs.cx.pos.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepo;

    @Override
    public List<Item> getAllItems() {
        List<Item> itemList = itemRepo.findAll();
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        itemList = itemList.stream().map(item -> {
            item.setItemId(item.get_id().toHexString());
            item.set_id(null);
            return item;
        }).collect(Collectors.toList());
        return itemList;
    }
}
