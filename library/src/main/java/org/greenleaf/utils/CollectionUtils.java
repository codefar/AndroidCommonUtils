package org.greenleaf.utils;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 */

public class CollectionUtils {
    private static final String DELIMITER = ",";

    /**
     * 判断集合是否为空
     *
     * @param c
     * @param <V>
     * @return
     */
    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.isEmpty());
    }

    /**
     * 判断集合是否不为空
     *
     * @param c
     * @param <V>
     * @return
     */
    public static <V> boolean isNotEmpty(Collection<V> c) {
        return !isEmpty(c);
    }
    /**
     * Join collection to string, separator is {@link #DELIMITER}
     *
     * @param tokens
     * @return
     */
    public static String join(Iterable tokens) {
        return tokens == null ? "" : TextUtils.join(DELIMITER, tokens);
    }

    /**
     * 将数组转为list
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> asList(T... array) {
        return Arrays.asList(array);
    }

    /**
     * 将数组转为set集合
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> Set<T> asSet(T... array) {
        return new HashSet<T>(asList(array));
    }

    /**
     * 集合转为数组
     *
     * @param c
     * @return
     */
    public static Object[] asArray(Collection<?> c) {
        if (!isEmpty(c)) {
            return c.toArray();
        }
        return null;
    }

}
