package org.alexk.models.viewmodels;

import lombok.Data;
import org.alexk.models.OrderItem;

/**
 * Clasa care contine date de vizualizare despre un produs dintr-o comanda
 */
public @Data class OrderItemViewModel extends OrderItem {
    /**
     * String care indica numele produsului
     */
    private String productName;
    /**
     * String care indica pretul produsului
     */
    private Double productPrice;
    /**
     * String care indica pretul total (cantitatea din comanda * pret)
     */
    private Double totalPrice;
}
