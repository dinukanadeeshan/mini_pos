package org.syscolabs.cx.pos.service;

import org.syscolabs.cx.pos.dto.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
}
