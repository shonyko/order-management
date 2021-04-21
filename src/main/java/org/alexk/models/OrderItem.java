package org.alexk.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.alexk.models.misc.BaseModel;

/**
 * Clasa care contine date despre un produs dintr-o comanda
 */
@EqualsAndHashCode(callSuper = true)
public @Data class OrderItem extends BaseModel {
    /**
     * Obiect ce reprezinta id-ul tuplei ce reprezinta un produs intr-o comanda
     */
    private Integer id;
    /**
     * Obiect ce reprezinta id-ul comenzii din care face parte produsul
     */
    private Integer orderId;
    /**
     * Obiect ce reprezinta id-ul produsului introdus in comanda
     */
    private Integer productId;
    /**
     * Obiect ce reprezinta cantitatea produsului din comanda
     */
    private Integer quantity;
}
