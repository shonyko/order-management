package org.alexk.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.alexk.models.misc.BaseModel;

/**
 * Clasa care contine date despre un client
 */
@EqualsAndHashCode(callSuper = true)
public @Data class Customer extends BaseModel {
    /**
     * Obiect care reprezinta id-ul clientului
     */
    private Integer id;
    /**
     * String care reprezinta numele clientului
     */
    private String name;
    /**
     * String care reprezinta adresa clientului
     */
    private String address;
    /**
     * String care reprezinta adresa de email a clientului
     */
    private String email;
    /**
     * Obiect care reprezinta varsta clientului
     */
    private Integer age;
}
