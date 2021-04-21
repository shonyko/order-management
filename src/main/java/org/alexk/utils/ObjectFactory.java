package org.alexk.utils;

import lombok.val;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care faciliteaza crearea obiectelor dintr-un rezultat provenit dintr-o interogare a bazei de date
 */
public class ObjectFactory {
    /**
     * Blocheaza crearea instantelor
     */
    private ObjectFactory() {}

    /**
     * @param clazz clasa obiectelor ce urmeaza sa fie create
     * @param res setul de resultate obtinut de la interogare
     * @param <T> tipul clasei obiectelor ce urmeaza sa fie create
     * @return o lista cu obiectele ce au fost create
     * @throws Exception cand apare o eroare
     */
    public static <T> List<T> createObjects(Class<T> clazz, ResultSet res) throws Exception {
        val list = new ArrayList<T>();

        try {

            while (res.next()) {
                val obj = clazz.getDeclaredConstructor().newInstance();

                for(val field : FieldRetriever.getFields(clazz)) {
                    val name = field.getName();

                    val value = res.getObject(name);
                    val propertyDescriptor = new PropertyDescriptor(name, clazz);
                    val setter = propertyDescriptor.getWriteMethod();
                    setter.invoke(obj, value);
                }

                list.add(obj);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return list;
    }
}
