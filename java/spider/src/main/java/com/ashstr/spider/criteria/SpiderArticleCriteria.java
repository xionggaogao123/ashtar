package com.ashstr.spider.criteria;

import com.ashstr.common.domain.PagingCriteria;
import lombok.Data;

import java.util.List;

/**
 * @author keven
 * @date 2017-12-17 下午2:35
 * @Description
 */
@Data
public class SpiderArticleCriteria extends PagingCriteria {

    private static final long serialVersionUID = 4625750463841833144L;

    private String sinceTime;

    private List<Integer> channels;


}
