package com.web.vt.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ValidationException extends RuntimeException{

    public ValidationException(String msg) {
        super(msg);
    }

}
