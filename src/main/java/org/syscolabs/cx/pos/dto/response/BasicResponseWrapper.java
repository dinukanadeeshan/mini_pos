package org.syscolabs.cx.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.syscolabs.cx.pos.enums.ResponseStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponseWrapper<T> {

    private int status;
    private ResponseStatus statusText;
    private T content;

}
