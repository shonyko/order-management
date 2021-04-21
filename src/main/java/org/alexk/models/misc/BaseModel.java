package org.alexk.models.misc;

/**
 * Clasa de baza pentru un model care ne asigura existenta unui id
 */
public class BaseModel implements DatabaseModel {
    /**
     * @return id-ul modelului
     */
    @Override
    public Integer getId() {
        return 0;
    }

    /**
     * @param id id-ul pe care dorim sa il setam modelului
     */
    @Override
    public void setId(Integer id) {
    }
}
