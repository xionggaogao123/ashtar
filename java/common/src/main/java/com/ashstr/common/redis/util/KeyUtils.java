package com.ashstr.common.redis.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * @author keven
 * @date 2018-06-03 上午9:23
 * @Description
 */
public abstract class KeyUtils {

    public KeyUtils() {

    }

    public static <T> String entityCount(Class<T> entityClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":count";
    }

    public static <T> String entityId(Class<T> entityClass, long id) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":" + id;
    }

    public static <T> String entityId(Class <T> entityClass, String id) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "id can not be null");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":" + id;
    }

}
