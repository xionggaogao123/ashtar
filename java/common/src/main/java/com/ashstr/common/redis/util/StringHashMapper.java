package com.ashstr.common.redis.util;

import com.ashstr.common.json.JsonMapper;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2018-06-02 下午11:08
 * @Description
 */
public class StringHashMapper<T> {

    private final JsonMapper mapper = JsonMapper.nonDefaultMapper();
    private final JavaType userType;
    private final JavaType mapType;

    public StringHashMapper(Class<T> type) {
        this.userType = this.mapper.getMapper().getTypeFactory().constructType(type);
        this.mapType = this.mapper.createCollectionType(HashMap.class, String.class, String.class);
    }


    public T fromHash(Map<String, String> hash) {
        return hash.isEmpty()?null: (T) this.mapper.getMapper().convertValue(hash, this.userType);
    }

    public Map<String, String> toHash(T object) {
        Map<String, String> hash = this.mapper.getMapper().convertValue(object, this.mapType);
        List<String> nullKeys = Lists.newArrayListWithCapacity(hash.size());
        Iterator iterator = hash.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            if(entry.getValue() == null) {
                nullKeys.add(entry.getKey());
            }
        }

        iterator = nullKeys.iterator();
        while(iterator.hasNext()) {
            String nullKey = (String)iterator.next();
            hash.remove(nullKey);
        }
        return hash;
    }
}
