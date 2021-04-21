package org.alexk.models.viewmodels;

import lombok.Data;
import org.alexk.models.Order;

/**
 * Clasa care contine date de vizualizare despre o comanda
 */
public @Data class OrderViewModel extends Order {
    /**
     * String care indica numele clientului
     */
    private String customerName;
    /**
     * String care indica pretul comenzii
     */
    private String price;
}
