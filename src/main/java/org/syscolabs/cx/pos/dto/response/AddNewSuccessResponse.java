package org.syscolabs.cx.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.syscolabs.cx.pos.enums.ResponseStatus;

@AllArgsConstructor
@Setter
@Getter
public class AddNewSuccessResponse extends BasicResponseWrapper<AddContent> {

    public AddNewSuccessResponse(String objectId, String entityType) {
        super(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, new AddContent(objectId, entityType));
    }
}

class AddContent {
    public String id;
    public String entityType;

    AddContent(String id, String entityType) {
        this.id = id;
        this.entityType = entityType;
    }
}