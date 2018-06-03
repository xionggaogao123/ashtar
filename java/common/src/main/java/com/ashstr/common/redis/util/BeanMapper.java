package com.ashstr.common.redis.util;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author keven
 * @date 2018-06-03 上午9:30
 * @Description
 */
public class BeanMapper {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    private BeanMapper() {

    }

    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();

        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }

        return destinationList;
    }

    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    public static Map<String, Object> convertObjectToMap(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> objectAsMap = new HashMap();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();

        for (PropertyDescriptor pd : propertyDescriptors) {
            Method reader = pd.getReadMethod();
            if (reader != null && !reader.isAccessible()) {
                reader.setAccessible(true);
            }

            if (reader != null) {
                objectAsMap.put(pd.getName(), reader.invoke(obj));
            }
        }

        return objectAsMap;
    }
}
