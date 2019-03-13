package org.syscolabs.cx.pos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.syscolabs.cx.pos.dto.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findBy_id(String _id);
}
