package org.alexk.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.alexk.models.misc.BaseModel;

/**
 * Clasa care contine date despre o comanda
 */
@EqualsAndHashCode(callSuper = true)
public @Data class Order extends BaseModel {
    /**
     * Obiect care reprezinta id-ul comenzii
     */
    private Integer id;
    /**
     * Obiect care reprezinta id-ul clientului care a facut comanda
     */
    private Integer customerId;
    /**
     * String care reprezinta data la care a fost facuta comanda
     */
    private String date;
}
