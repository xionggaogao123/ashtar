package com.ashstr.common.domain;


import com.ashstr.common.base.BaseCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author keven
 * @date 2018-01-26 下午3:20
 * @Description
 */
public class PagingCriteria extends BaseCriteria {

    private static final long serialVersionUID = 7274617154067354808L;

    @Setter
    @Getter
    private Integer pageNo = 1;

    @Setter
    @Getter
    private Integer pageSize = 20;

    @Setter
    @Getter
    private Boolean hasNext = true;

    public PagingCriteria() {

    }

    public void nextPage() {
        if (this.pageNo == null) {
            this.pageNo = 1;
        }
        this.pageNo = this.pageNo + 1;
    }

    public Integer getLimit() {
        PageInfo pageInfo = new PageInfo(this.pageNo, this.pageSize);
        return pageInfo.getLimit();
    }

    public Integer getOffset() {
        PageInfo pageInfo = new PageInfo(this.pageNo, this.pageSize);
        return pageInfo.getOffset();
    }

    @Override
    public Map<String, Object> toMap() {
        this.formatDate();
        return super.toMap();
    }

    protected void formatDate() {

    }


}
