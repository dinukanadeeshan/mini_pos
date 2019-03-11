package org.syscolabs.cx.pos.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.syscolabs.cx.pos.model.Pets;
import org.syscolabs.cx.pos.repository.PetsRepository;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsController {
    @Autowired
    private PetsRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Pets> getAllPets() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Pets getPetById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePet(@Validated Pets pet) {
        System.out.println(pet);
        return "saved";
    }
}
