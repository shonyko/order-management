package org.alexk.models.misc;

import lombok.val;
import org.alexk.utils.StringFormatter;

/**
 * Clasa care faciliteaza crearea unui header pentru tabelul din interfata vizuala
 */
public class Column {
    /**
     * String folosit pentru crearea unui header al tabelului
     */
    private final String data;
    /**
     * String care indica numele atributului
     */
    private final String name;
    /**
     * String care indica titlul header-ului de tabel
     */
    private final String title;

    /**
     * Creeaza un nou header de tabel
     * @param name numele header-ului
     */
    public Column(String name) {
        this.data = name;
        this.name = name;

        val words = StringFormatter.splitCamelCase(name);
        this.title = StringFormatter.capitalize(words);
    }

    /**
     * Metoda care transpune acest obiect sub forma de text
     * @return un string ce reprezinta obiectul curent sub forma unui JSON
     */
    public String toString() {
        val words = StringFormatter.splitCamelCase(name);
        val title = StringFormatter.capitalize(words);

        return String.format(
                "{\"data\": \"%s\", \"name\": \"%s\", \"title\": \"%s\"}",
                name, name, title
        );
    }
}