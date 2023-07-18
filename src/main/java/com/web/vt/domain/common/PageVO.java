package com.web.vt.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PageVO {
    private int page;
    private int size;

}
