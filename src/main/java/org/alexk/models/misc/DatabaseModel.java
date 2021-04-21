package org.alexk.models.misc;

/**
 * Interfata folosita pentru a asigura ca modelelefolosite pentru baza de date au campul id
 */
public interface DatabaseModel {
    /**
     * @return id-ul modelului
     */
    Integer getId();

    /**
     * @param id id-ul pe care dorim sa il setam modelului
     */
    void setId(Integer id);
}
