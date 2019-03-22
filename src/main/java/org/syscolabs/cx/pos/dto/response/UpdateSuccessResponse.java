package org.syscolabs.cx.pos.dto.response;

import org.syscolabs.cx.pos.enums.ResponseStatus;

public class UpdateSuccessResponse extends BasicResponseWrapper<UpdateContent> {

    public UpdateSuccessResponse(String id, String entityType) {
        super(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, new UpdateContent(id, entityType));
    }
}


class UpdateContent {
    public String id;
    public String entityType;

    UpdateContent(String id, String entityType) {
        this.id = id;
        this.entityType = entityType;
    }
}