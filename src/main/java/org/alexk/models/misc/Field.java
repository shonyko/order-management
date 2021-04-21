package org.alexk.models.misc;

import lombok.Data;
import org.alexk.models.enums.FieldType;

/**
 * Clasa care faciliteaza crearea unui camp de adaugat intr-un formular
 */
public @Data class Field {
    /**
     * String care indica tipul campului
     */
    private String type;

    // SIMPLE
    /**
     * String care indica valoarea etichetei campului
     */
    private String label;
    /**
     * String care indica numele din formular al campului
     */
    private String name;
    /**
     * String care indica valoarea campului
     */
    private String value;

    // SELECT
    /**
     * String care indica adresa de unde se vor extrage date pentru select
     */
    private String url;

    // GENERAL
    /**
     * Atribut care indica daca campul va fi nemodificabil
     */
    private boolean readonly;
    /**
     * Atribut care indica daca campul va fi ascuns
     */
    private boolean hidden;

    /**
     * Creeaza un nou camp de folosit intr-un formular
     * @param label eticheta campului
     * @param name numele campului
     */
    public Field(String label, String name) {
        this(FieldType.SIMPLE.name(), label, name, null, null, false, false);
    }

    /**
     * Creeaza un nou camp de folosit intr-un formular
     * @param label eticheta campului
     * @param name numele campului
     * @param value valoarea campului
     */
    public Field(String label, String name, String value) {
        this(FieldType.SIMPLE.name(), label, name, value, null, false, false);
    }

    /**
     * Creeaza un nou camp de folosit intr-un formular
     * @param label eticheta campului
     * @param name numele campului
     * @param value valoarea campului
     * @param url adresa de unde campul va extrage date
     * @param readonly proprietatea campului de a fi nemodificabil
     * @param hidden proprietatea campului de a fi ascuns
     */
    public Field(String type, String label, String name, String value, String url, boolean readonly, boolean hidden) {
        this.type = type;
        this.label = label;
        this.name = name;
        this.value = value;
        this.url = url;
        this.readonly = readonly;
        this.hidden = hidden;
    }
}
