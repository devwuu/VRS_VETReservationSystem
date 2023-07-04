package com.web.vt.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg) {
        super(msg);
    }

}
