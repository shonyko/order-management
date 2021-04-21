package org.alexk.exceptions;

/**
 * Exceptie care indica faptul ca a aparut in timpul lucrului cu modele
 */
public class ModelException extends Exception {
    /**
     * Creeaza o noua exceptie
     * @param message mesajul continut de exceptie
     */
    public ModelException(String message) {
        super(message);
    }
}
