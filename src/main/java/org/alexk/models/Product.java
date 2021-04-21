package org.alexk.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.alexk.models.misc.BaseModel;

/**
 * Clasa care contine date despre un produs
 */
@EqualsAndHashCode(callSuper = true)
public @Data class Product extends BaseModel {
    /**
     * Obiect ce reprezinta id-ul produsului
     */
    private Integer id;
    /**
     * String ce reprezinta numele produsului
     */
    private String name;
    /**
     * Obiect ce reprezinta pretul produsului
     */
    private Double price;
    /**
     * Obiect ce reprezinta cantitatea exista in stock din produs
     */
    private Integer quantity;
}
