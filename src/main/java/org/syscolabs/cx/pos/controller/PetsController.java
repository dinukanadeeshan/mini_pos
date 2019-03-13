package org.syscolabs.cx.pos.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.syscolabs.cx.pos.dto.model.Pets;
import org.syscolabs.cx.pos.repository.PetsRepository;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/pets")
public class PetsController {
    @Autowired
    private PetsRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/hal+json"})
    public Resources<Pets> getAllPets() {
        List<Pets> pets = repository.findAll();
        for (Pets pet : pets) {
            Link selfLink = linkTo(methodOn(PetsController.class)
                    .getPetById(pet.get_id())).withSelfRel();
            pet.add(selfLink);
        }

        Link link = linkTo(methodOn(PetsController.class)
                .getAllPets()).withSelfRel();
        Resources<Pets> result = new Resources<>(pets, link);
        return result;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Pets getPetById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePet(@Validated Pets pet) {
        System.out.println(pet);
        return "saved";
    }
}
