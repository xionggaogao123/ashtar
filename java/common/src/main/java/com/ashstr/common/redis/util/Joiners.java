package com.ashstr.common.redis.util;

import com.google.common.base.Joiner;

/**
 * @author keven
 * @date 2018-06-03 上午9:34
 * @Description
 */
public class Joiners {

    public static final Joiner DOT = Joiner.on(".").skipNulls();
    public static final Joiner COMMA = Joiner.on(",").skipNulls();
    public static final Joiner COLON = Joiner.on(":").skipNulls();

}
