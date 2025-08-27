package com.example.pro.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApprenticeUtil {
    private static final Pattern HUMP = Pattern.compile("[A-Z]");
    private static final Pattern LINE = Pattern.compile("_(\\w)");

    /** camelCase -> snake_case */
    public static String humpToLine(String str) {
        Matcher m = HUMP.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) m.appendReplacement(sb, "_" + m.group(0).toLowerCase());
        m.appendTail(sb);
        return sb.toString();
    }

    /** snake_case -> camelCase */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher m = LINE.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) m.appendReplacement(sb, m.group(1).toUpperCase());
        m.appendTail(sb);
        return sb.toString();
    }

    /** Build QueryWrapper<E> from non-null fields of entity */
    public static <E> QueryWrapper<E> getQueryWrapper(E entity) {
        QueryWrapper<E> wrapper = new QueryWrapper<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isFinal(f.getModifiers())) continue;
            f.setAccessible(true);
            try {
                Object v = f.get(entity);
                if (!ObjectUtils.isEmpty(v)) {
                    wrapper.eq(humpToLine(f.getName()), v);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return wrapper;
    }

    /** Reflective getter: returns entity.value (e.g., "id", "name") */
    public static <E> Object getValueForClass(E entity, String fieldName) {
        try {
            Field f = entity.getClass().getDeclaredField(fieldName);
            PropertyDescriptor pd = new PropertyDescriptor(f.getName(), entity.getClass());
            return pd.getReadMethod().invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}