package org.syscolabs.cx.pos.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.syscolabs.cx.pos.dto.model.Pets;

public interface PetsRepository extends MongoRepository<Pets, String> {
    Pets findBy_id(ObjectId _id);
}