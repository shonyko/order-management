package org.alexk.utils;

import lombok.val;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care faciliteaza obtinerea atributelor declarate
 */
public class FieldRetriever {
    /**
     * Blocheaza crearea instantelor
     */
    private FieldRetriever() {}

    /**
     * @param clazz clasa pentru care dorim sa extragem atributele
     * @return o lista cu toate atributele unei clase
     */
    public static List<Field> getFields(Class<?> clazz) {
        if(clazz.equals(Object.class)) {
            return new ArrayList<>();
        }

        val parentClass = clazz.getSuperclass();
        val superFields = getFields(parentClass);

        for(val field : clazz.getDeclaredFields()) {
            superFields.add(field);
        }

        return superFields;
    }
}
