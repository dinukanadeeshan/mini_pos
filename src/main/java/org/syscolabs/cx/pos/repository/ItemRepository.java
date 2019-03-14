package org.syscolabs.cx.pos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.syscolabs.cx.pos.dto.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {
}
