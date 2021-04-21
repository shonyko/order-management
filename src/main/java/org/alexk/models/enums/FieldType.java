package org.alexk.models.enums;

/**
 * Enumerare folosita pentru a determina tipul campului din formularul de inserare / actualizare din interfata grafica
 */
public enum FieldType {
    /**
     * Tip simplu => Label + Input Field
     */
    SIMPLE,
    /**
     * Tip select => Label + ComboBox
     */
    SELECT,
    /**
     * Tip datetime => Label + Datetime Picker
     */
    DATETIME
}
