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

    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.isEmpty());
    }

    public static <V> boolean isNotEmpty(Collection<V> c) {
        return !isEmpty(c);
    }

    public static String join(Iterable tokens) {
        return tokens == null ? "" : TextUtils.join(DELIMITER, tokens);
    }

    public static <T> List<T> asList(T... array) {
        return Arrays.asList(array);
    }

    public static <T> Set<T> asSet(T... array) {
        return new HashSet<T>(asList(array));
    }

    public static Object[] asArray(Collection<?> c) {
        if (!isEmpty(c)) {
            return c.toArray();
        }
        return null;
    }
}
