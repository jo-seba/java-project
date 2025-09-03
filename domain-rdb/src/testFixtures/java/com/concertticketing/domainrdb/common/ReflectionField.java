package com.concertticketing.domainrdb.common;

import java.lang.reflect.Field;

public final class ReflectionField {
    public static void setField(Object target, String field, Object value) {
        try {
            Field declaredField = target.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
