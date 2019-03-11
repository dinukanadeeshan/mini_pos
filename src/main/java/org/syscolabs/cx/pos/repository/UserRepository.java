package org.syscolabs.cx.pos.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.syscolabs.cx.pos.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
