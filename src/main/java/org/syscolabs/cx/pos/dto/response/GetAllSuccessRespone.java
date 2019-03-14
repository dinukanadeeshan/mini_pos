package org.syscolabs.cx.pos.dto.response;

import org.syscolabs.cx.pos.enums.ResponseStatus;

import java.util.List;

public class GetAllSuccessRespone<T> extends BasicResponseWrapper<GetAllContent<T>> {
    public GetAllSuccessRespone(List<T> list, String entityType) {
        super(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS, new GetAllContent<T>(list, entityType));
    }
}


class GetAllContent<T> {
    public List<T> list;
    public String entityType;

    GetAllContent(List<T> list, String entityType) {
        this.list = list;
        this.entityType = entityType;
    }
}