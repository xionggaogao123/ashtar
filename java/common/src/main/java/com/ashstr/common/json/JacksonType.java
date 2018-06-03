package com.ashstr.common.json;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-27 下午10:10
 * @Description
 */
public class JacksonType {

    public static final TypeReference<List<String>> LIST_OF_STRING = new TypeReference<List<String>>() {};

    public static final TypeReference<Map<String, String>> MAP_OF_STRING = new TypeReference<Map<String, String>>() {};

    public static final TypeReference<Map<String, Integer>> MAP_OF_INTEGER = new TypeReference<Map<String, Integer>>() {};
}
