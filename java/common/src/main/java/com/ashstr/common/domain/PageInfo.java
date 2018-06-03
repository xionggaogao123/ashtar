package com.ashstr.common.domain;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * @author keven
 * @desc
 */
public class PageInfo {
    
    public static final String LIMIT = "limit";

    public static final String OFFSET = "offset";

    @Getter
    @Setter
    private  Integer offset;

    @Getter
    @Setter
    private Integer limit;
    
    public PageInfo() {

    }
    
    public static PageInfo of(Integer pageNo, Integer size) {
        return new PageInfo(pageNo, size);
    }
    
    public PageInfo(Integer pageNo, Integer size) {
        pageNo = MoreObjects.firstNonNull(pageNo, 1);
        size = MoreObjects.firstNonNull(size, 20);
        limit = size > 0 ? size : 20;
        offset = (pageNo - 1) * size;
        offset = offset > 0 ? offset : 0;
    }

    
}
