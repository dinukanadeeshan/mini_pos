package org.syscolabs.cx.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@AllArgsConstructor
@Setter
@Getter
public class AddNewResponse {
    private ObjectId objectId;
}
