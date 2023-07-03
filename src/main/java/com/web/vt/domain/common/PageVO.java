package com.web.vt.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
//@Accessors(fluent = true, chain = true)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PageVO {
    private int page;
    private int size;

}
