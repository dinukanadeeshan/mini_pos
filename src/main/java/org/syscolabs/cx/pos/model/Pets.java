package org.syscolabs.cx.pos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pets {
    @Id
    public ObjectId _id;

    public String name;

    public String species;

    @NotNull(message = "Ohh where is breed man???")
    public String breed;


    @Override
    public String toString() {
        return String.format("name : %s, species : %s\n", this.name, this.species);
    }
}
